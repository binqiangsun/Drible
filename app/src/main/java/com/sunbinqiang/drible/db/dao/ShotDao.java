package com.sunbinqiang.drible.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sunbinqiang.drible.db.entity.Shot;


/**
 * Created by sunbinqiang on 01/06/2017.
 */
@Dao
public interface ShotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Shot[] shots);

    @Query("select * from shots where shots.page = :pageId order by shots.ind asc")
    LiveData<Shot[]> loadShots(int pageId);

    @Query("select * from shots where shots.page = :pageId order by shots.ind asc")
    Shot[] loadShotSync(int pageId);

    @Query("delete from shots where shots.page = :pageId")
    void deleteShots(int pageId);

}
