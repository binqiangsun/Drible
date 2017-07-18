package com.lukou.service.account;

import com.lukou.service.account.bean.User;

/**
 *
 * @author Xu
 */

public class SimpleAccountListener implements AccountListener {

    @Override
    public void onAccountChanged(AccountService sender, User oldUser) {

    }

    @Override
    public void onProfileChanged(AccountService sender) {

    }
}
