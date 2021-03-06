package com.sunbinqiang.drible.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lukou.service.http.Resource;
import com.sunbinqiang.drible.db.DatabaseCreator;
import com.sunbinqiang.drible.db.dao.ShotDao;
import com.sunbinqiang.drible.db.entity.Shot;

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

    private ShotDao shotDao;
    private ExecutorService executor;

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
    public void getSelectedShots(final int page, MutableLiveData<Resource<Shot[]>> liveData){
        getShotsFromNet(page, liveData);
    }

    private void getShotsFromNet(int page, final MutableLiveData<Resource<Shot[]>> shotsLiveData){
        Log.d("shotRepo", "get shots from net");
        RepositoryUtils.getApiService().getShots(page).enqueue(new Callback<Shot[]>() {
            @Override
            public void onResponse(Call<Shot[]> call, Response<Shot[]> response) {
                // error case is left out for brevity
                Shot[] shots = response.body();
                if (response.isSuccessful() && shots != null) {
                    addLiveData(shotsLiveData, response.body());
                } else {
                    shotsLiveData.setValue(Resource.error("data is null", (Shot[])null));
                }
            }

            @Override
            public void onFailure(Call<Shot[]> call, Throwable t) {
                shotsLiveData.setValue(Resource.error(t.getMessage(), (Shot[])null));
            }
        });
    }

    private void insertDbShots(Shot[] netShots){
        shotDao.insertAll(netShots);
    }

    private void addLiveData(MutableLiveData<Resource<Shot[]>> liveData, final Shot[] addList) {
        liveData.setValue(Resource.success(addList));
        RepositoryUtils.repository_executor.execute(new Runnable() {
            @Override
            public void run() {
                insertDbShots(addList);
            }
        });
    }


}
