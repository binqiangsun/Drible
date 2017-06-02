package com.sunbinqiang.drible;

import com.lukou.service.LibApplication;
import com.lukou.service.account.AccountService;
import com.lukou.service.config.ConfigService;
import com.lukou.service.debug.DebugService;
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

    @Override
    public ConfigService configService() {
        return null;
    }

    @Override
    public AccountService accountService() {
        return null;
    }

    @Override
    public DebugService debugService() {
        return null;
    }
}
