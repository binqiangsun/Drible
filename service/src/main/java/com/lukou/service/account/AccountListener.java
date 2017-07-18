package com.lukou.service.account;


import com.lukou.service.account.bean.User;

/**
 * AccountListener与LoginResultListener区别在于，
 * AccountListener类似广播事件。
 *
 * @author Xu
 */

public interface AccountListener {

    /**
     * 用户登录或者退出登录是会被call到
     *
     * @param sender
     * @param oldUser 原先的用户
     */
    void onAccountChanged(AccountService sender, User oldUser);

    /**
     * 用户登录或者退出登录时不会被call到，用户信息改变时才会被call到。
     */
    void onProfileChanged(AccountService sender);


}
