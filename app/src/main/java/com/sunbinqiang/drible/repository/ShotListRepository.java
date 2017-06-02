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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

/**
 * Created by sunbinqiang on 28/05/2017.
 */

public class ShotListRepository {

    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private ShotDao shotDao;
    private ExecutorService executor;
    private LiveData<List<Shot>> mObservableShots;

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

    public ShotListRepository() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Shot>> getSelectedShots(final int page){


        LiveData<Boolean> databaseCreated = DatabaseCreator.getInstance().isDatabaseCreated();
        mObservableShots = Transformations.switchMap(databaseCreated,
                new Function<Boolean, LiveData<List<Shot>>>() {
                    @Override
                    public LiveData<List<Shot>> apply(Boolean isDbCreated) {
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
        return mObservableShots;
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
                    List<Shot> shots = new ArrayList<>(Arrays.asList(response.body()));
                    Log.d("shotRepository", "3, get network is : " + String.valueOf(shots == null ? 0 : shots.size()));
                    if (page == 0) {
                        compareAndInsertDb(shotDao.loadShotSync(), shots);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void compareAndInsertDb(List<Shot> dbShots, List<Shot> netShots){
        if (netShots == null) {
            return;
        } else if (dbShots == null) {
            shotDao.insertAll(netShots);
            return;
        }

        //compare
//        if(dbShots.size() != netShots.size()) {
//            shotDao.deleteShots();
//            shotDao.insertAll(netShots);
//        }
//        ListIterator<Shot> e1 = dbShots.listIterator();
//        ListIterator<Shot> e2 = netShots.listIterator();
//
//        while (e1.hasNext() && e2.hasNext()) {
//            Shot o1 = e1.next();
//            Shot o2 = e2.next();
//            if (!(o1==null ? o2==null : o1.equals(o2))) {
//                //not equal
//                shotDao.deleteShots();
//                shotDao.insertAll(netShots);
//                return;
//            }
//        }
        Log.d("shotReposi", "db is equal to net");
    }

}
