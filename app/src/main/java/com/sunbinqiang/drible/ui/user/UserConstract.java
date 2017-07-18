package com.sunbinqiang.drible.ui.user;

import android.content.Intent;

import com.lukou.service.account.bean.User;

/**
 * Created by sunbinqiang on 24/06/2017.
 */

public interface UserConstract {

    interface Presenter {
        void start(Intent intent);
        void getUserByCode(String code); //个人主页， 登录返回的code
        void getUserById(int userId);   // 用户信息界面， 别人的个人主页
        void getProfileUser();
    }

    interface View {
        void setPresenter(Presenter presenter);
        void showUser(User user);
    }
}
