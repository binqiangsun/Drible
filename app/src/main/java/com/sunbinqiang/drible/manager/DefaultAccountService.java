package com.sunbinqiang.drible.manager;

import android.content.Context;

import com.lukou.service.account.AccountListener;
import com.lukou.service.account.BaseAccountService;

/**
 * @author Xu
 */

public class DefaultAccountService extends BaseAccountService {

    public DefaultAccountService(Context context) {
        super(context);
    }

    @Override
    public void login(Context context) {
        login(context, null);
    }

    @Override
    public void login(Context context, AccountListener listener) {
    }
}
