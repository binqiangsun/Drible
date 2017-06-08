package com.sunbinqiang.drible.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lukou.service.http.Resource;
import com.sunbinqiang.drible.db.DatabaseCreator;
import com.sunbinqiang.drible.db.dao.ShotDao;
import com.sunbinqiang.drible.db.entity.Shot;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunbinqiang on 28/05/2017.
 * 分页列表中的数据没有办法实时更新
 */

public class ShotListRepository {

    private static final int MAX_CACHE_PAGE = 10; //前10页内容缓存数据库
    private static final int MSG_DATABASE = 1001;

    private static final MutableLiveData LOADING_DATA = new MutableLiveData();
    {
        //noinspection unchecked
        LOADING_DATA.setValue(Resource.loading(null));
    }

    private ShotDao shotDao;
    private ExecutorService executor;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_DATABASE) {
                if (shotDao == null) {
                    //数据库创建超时，从网络获取数据
                    getShotsFromNet(msg.arg1, (MutableLiveData<Resource<Shot[]>>)msg.obj);
                } else {
                    getShotsFromDb(msg.arg1, (MutableLiveData<Resource<Shot[]>>)msg.obj);
                }
            }
            return false;
        }
    });

    private static ShotListRepository instance;

    public static ShotListRepository getInstance(){
        if (instance == null) {
            synchronized (ShotListRepository.class) {
                if(instance == null) {
                    instance = new ShotListRepository();
                }
            }
        }
        return instance;
    }

    private ShotListRepository() {
        this.executor = Executors.newSingleThreadExecutor();
        DatabaseCreator.getInstance().isDatabaseCreated()
                .observeForever(new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean isCreated) {
                        if (isCreated && DatabaseCreator.getInstance().getDatabase() != null) {
                            shotDao = DatabaseCreator.getInstance().getDatabase().shotDao();
                        }
                    }
                });
    }

    /**
     * 获取首页列表数据
     * @param page
     * @return
     */
    public LiveData<Resource<Shot[]>> getSelectedShots(final int page){
        MutableLiveData<Resource<Shot[]>> shotsLiveData = new MutableLiveData<>();
        if (page < MAX_CACHE_PAGE) {
            getShotsFromDb(page, shotsLiveData);
        } else {
            getShotsFromNet(page, shotsLiveData);
        }
        return shotsLiveData;
    }

    private MutableLiveData<Resource<Shot[]>> getShotsFromNet(int page, final MutableLiveData<Resource<Shot[]>> shotsLiveData){
        Log.d("shotRepo", "get shots from net");
        RepositoryUtils.getApiService().getShots(page).enqueue(new Callback<Shot[]>() {
            @Override
            public void onResponse(Call<Shot[]> call, Response<Shot[]> response) {
                // error case is left out for brevity
                Shot[] shots = response.body();
                if (response.isSuccessful() && shots != null) {
                    shotsLiveData.setValue(Resource.success(response.body()));
                } else {
                    shotsLiveData.setValue(Resource.error("data is null", (Shot[])null));
                }
            }

            @Override
            public void onFailure(Call<Shot[]> call, Throwable t) {
                shotsLiveData.setValue(Resource.error(t.getMessage(), (Shot[])null));
            }
        });
        return shotsLiveData;
    }

    private LiveData<Resource<Shot[]>> getShotsFromDb(final int page, final MutableLiveData<Resource<Shot[]>> shotsLiveData) {
        Log.d("shotRepo", "get shots from db");
        shotsLiveData.setValue(Resource.loading((Shot[])null));

        if (shotDao == null) {
            Log.d("shotRepo", "db is null");
            Message msg = Message.obtain();
            msg.what = MSG_DATABASE;
            msg.arg1 = page;
            msg.obj = shotsLiveData;
            mHandler.sendMessageDelayed(msg, 3000);
        } else {
            refreshShots(page);
            final LiveData<Shot[]> dbLiveData = shotDao.loadShots(page);
            dbLiveData.observeForever(new Observer<Shot[]>() {
                @Override
                public void onChanged(@Nullable Shot[] shots) {
                    if (shots != null && shots.length > 0) {
                        Log.d("shotRepo", "db shots changed not null");
                        dbLiveData.removeObserver(this);
                        shotsLiveData.setValue(Resource.success(shots));
                    } else {
                        Log.d("shotRepo", "db shots changed null");
                    }
                }
            });
        }
        return shotsLiveData;
    }

    private void refreshShots(final int page){
        Log.d("shotRepo", "refresh shots");
        RepositoryUtils.getApiService().getShots(page).enqueue(new Callback<Shot[]>() {
            @Override
            public void onResponse(Call<Shot[]> call, Response<Shot[]> response) {
                Log.d("shotRepo", "refresh shots success");
                final Shot[] shots = response.body();
                if (page < MAX_CACHE_PAGE) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            compareAndInsertDb(shotDao.loadShotSync(page), shots, page);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Shot[]> call, Throwable t) {
                Log.d("shotRepo", "refresh net error");
            }
        });
    }

    private void compareAndInsertDb(Shot[] dbShots, Shot[] netShots, int page){
        if (netShots == null) {
            Log.d("shotReposi", "net is null");
            return;
        } else if (dbShots == null) {
            shotDao.insertAll(netShots);
            Log.d("shotReposi", "db is null");
            return;
        }

        if (!Arrays.equals(dbShots, netShots)) {
            // not equal
            insertDbShots(netShots, page);
            Log.d("shotReposi", "db is not equal to net");
        } else {
            Log.d("shotReposi", "db is equal to net");
        }
    }

    private void insertDbShots(Shot[] netShots, int page){
        //数据库中记录初始顺序
        int index = 0;
        for (Shot shot : netShots) {
            shot.setPage(page);
            shot.setInd(index ++);
        }
        // 仅仅删除当页的数据
        shotDao.deleteShots(page);
        shotDao.insertAll(netShots);
    }

}
