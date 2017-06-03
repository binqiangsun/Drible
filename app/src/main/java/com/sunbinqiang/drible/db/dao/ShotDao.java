package com.sunbinqiang.drible.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sunbinqiang.drible.db.entity.Shot;

import java.util.List;


/**
 * Created by sunbinqiang on 01/06/2017.
 */
@Dao
public interface ShotDao {
    @Query("SELECT * FROM shots")
    LiveData<List<Shot>> loadAllShots();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Shot> shots);

    @Query("select * from shots order by shots.ind asc")
    LiveData<List<Shot>> loadShots();

    @Query("select * from shots order by shots.ind asc")
    List<Shot> loadShotSync();

    @Query("delete from shots")
    void deleteShots();

}
