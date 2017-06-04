package com.sunbinqiang.drible;

import com.lukou.service.LibApplication;
import com.sunbinqiang.drible.db.DatabaseCreator;

/**
 * Created by sunbinqiang on 01/06/2017.
 */

public class MainApplication extends LibApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseCreator.getInstance().createDb(this);
    }

}
