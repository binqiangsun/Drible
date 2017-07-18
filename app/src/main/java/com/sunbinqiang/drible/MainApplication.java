package com.sunbinqiang.drible;

import com.lukou.service.LibApplication;
import com.lukou.service.account.AccountService;
import com.sunbinqiang.drible.db.DatabaseCreator;
import com.sunbinqiang.drible.manager.ServiceManager;

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
    public AccountService accountService() {
        return (AccountService) ServiceManager.instance(this).getService(ServiceManager.ServiceName.ACCOUNT_SERVICE);
    }

}
