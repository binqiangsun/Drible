package com.sunbinqiang.drible.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.sunbinqiang.drible.db.converter.DribleConverter;
import com.sunbinqiang.drible.db.dao.ShotDao;
import com.sunbinqiang.drible.db.entity.Shot;

/**
 * Created by sunbinqiang on 01/06/2017.
 */

@Database(entities = {Shot.class}, version = 1)
@TypeConverters(DribleConverter.class)
public abstract class DribleDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "drible_db";

    public abstract ShotDao shotDao();

}
