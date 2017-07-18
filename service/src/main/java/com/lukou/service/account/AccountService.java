package com.lukou.service.account;

import android.content.Context;

import com.lukou.service.account.bean.User;


/**
 * 账户服务: 负责登录，第三方帐号登录，注册
 * <p>
 * 1. 需要关心Account的页面，要先profile()判断是否有登录 没有登录的情况下，call saveUser(LoginResultListener
 * listener)
 * <p>
 * 2. 需要关心Account的页面, 要call addListener(AccountListener listener)
 * <p>
 * 3. 登录成功后，会先call LoginResultListener.onLoginSuccess, 然后再call
 * AccountListener.onAccountChanged。 对于同时关心LoginResultListener
 * .onLoginSuccess和AccountListener.onAccountChanged事件的页面要注意。
 */
public interface AccountService {

    /**
     * 获取完整的用户账户信息
     * <p>
     * 没有登录时返回null <br>
     */
    User user();

    /**
     * 没有登录时返回0
     */
    long id();

    /**
     * 没有登录时返回0
     */
    String token();

    void login(Context context);

    void login(Context context, AccountListener listener);

    void logout();

    void saveUser(User user);

    void saveToken(String token);

    /**
     * 未登录时，进行登录，并触发AccountListener.onAccountChanged事件
     * <p>
     * <p>
     * 已登录时，更新当前帐号信息
     * <p>
     * 只更新增量字段，如传入的profile只包含Avatar，则只更新Avatar字段，其他字段值不受影响。<br>
     * 如传入的profile中带有Token，则Token被忽略<br>
     * 如传入的profile中带有UserID，则必须与当前的UserID一致，否则忽略<br>
     * 任意字段更新都会触发AccountListener.onProfileChanged事件<br>
     */
    void update(User user);

    /**
     * 用Activity implement AccountListener,在Activity里面会帮你removeListener
     * addListener必须和removeListener配合使用，以免造成内存泄露
     * </p>注意listener里面不要做耗时的动作
     */
    void addListener(AccountListener listener);

    /**
     * 用Activity implement AccountListener,在Activity里面会帮你removeListener
     */
    void removeListener(AccountListener listener);

    boolean isLogin();
}
