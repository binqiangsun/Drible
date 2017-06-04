package com.sunbinqiang.drible.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.util.Log;

import com.sunbinqiang.drible.db.DatabaseCreator;
import com.sunbinqiang.drible.db.dao.ShotDao;
import com.sunbinqiang.drible.db.entity.Shot;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunbinqiang on 28/05/2017.
 */

public class ShotListRepository {

    private static final int MAX_CACHE_PAGE = 3; //前3页内容缓存数据库

    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

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
    }

    /**
     *
     * @param page
     * @return
     */
    public LiveData<Shot[]> getSelectedShots(final int page){
        if (page < MAX_CACHE_PAGE) {
            return getShotsFromDb(page);
        } else {
            return getShotsFromNet(page);
        }
    }

    private LiveData<Shot[]> getShotsFromNet(int page){
        final MutableLiveData<Shot[]> data = new MutableLiveData<>();
        RepositoryUtils.getApiService().getShots(page).enqueue(new Callback<Shot[]>() {
            @Override
            public void onResponse(Call<Shot[]> call, Response<Shot[]> response) {
                // error case is left out for brevity
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Shot[]> call, Throwable t) {

            }
        });
        return data;
    }

    private LiveData<Shot[]> getShotsFromDb(final int page) {
        LiveData<Boolean> databaseCreated = DatabaseCreator.getInstance().isDatabaseCreated();
        final LiveData<Shot[]> data = Transformations.switchMap(databaseCreated,
                new Function<Boolean, LiveData<Shot[]>>() {
                    @Override
                    public LiveData<Shot[]> apply(Boolean isDbCreated) {
                        if (!Boolean.TRUE.equals(isDbCreated)) { // Not needed here, but watch out for null
                            Log.d("shotRepository", "1, database is not created ");
                            return ABSENT;
                        } else {
                            refreshShots(page);
                            if (DatabaseCreator.getInstance().getDatabase() == null ||
                                    DatabaseCreator.getInstance().getDatabase().shotDao() == null) {
                                return ABSENT;
                            }
                            shotDao = DatabaseCreator.getInstance().getDatabase().shotDao();
                            return shotDao.loadShots();
                        }
                    }
                });
        return data;
    }

    private void refreshShots(final int page){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Shot[]> response = RepositoryUtils.getApiService().getShots(page).execute();
                    if (response == null || response.body() == null) {
                        return;
                    }
                    Shot[] shots = response.body();
                    if (page < MAX_CACHE_PAGE) {
                        compareAndInsertDb(shotDao.loadShotSync(), shots);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void compareAndInsertDb(Shot[] dbShots, Shot[] netShots){
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
            insertDbShots(netShots);
            Log.d("shotReposi", "db is not equal to net");
        } else {
            Log.d("shotReposi", "db is equal to net");
        }
    }

    private void insertDbShots(Shot[] netShots){
        //数据库中记录初始顺序
        int index = 0;
        for (Shot shot : netShots) {
            shot.setInd(index ++);
        }
        shotDao.deleteShots();
        shotDao.insertAll(netShots);
    }

}
