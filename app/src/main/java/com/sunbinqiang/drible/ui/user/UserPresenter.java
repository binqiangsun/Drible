package com.sunbinqiang.drible.ui.user;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.lukou.service.account.bean.User;
import com.sunbinqiang.drible.MainApplication;
import com.sunbinqiang.drible.bean.Token;
import com.sunbinqiang.drible.repository.RepositoryUtils;
import com.sunbinqiang.drible.ui.user.UserConstract.Presenter;
import com.sunbinqiang.drible.util.Constants;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by sunbinqiang on 05/07/2017.
 */

public class UserPresenter implements Presenter {

    private UserConstract.View mView;

    public UserPresenter(UserConstract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start(Intent intent) {
        String code = null;
        Uri uri = intent.getData();
        if (uri != null) {
            code = uri.getQueryParameter("code");
        }
        int userId = intent.getIntExtra("userId", 0);
        //
        if (!TextUtils.isEmpty(code)) {
            getUserByCode(code);  //第一次登录，查看自己的个人主页
        } else if (userId > 0) {
            getUserById(userId); //查看其他人的个人主页
        } else {
            getProfileUser();   //查看自己的个人主页
        }
    }

    @Override
    public void getUserByCode(String code) {
        RepositoryUtils.transform(
                RepositoryUtils.getApiService().getToken(Constants.CLIENT_ID, Constants.CLIENT_SCERET, code))
                .flatMap(new Func1<Token, Observable<User>>() {
                    @Override
                    public Observable<User> call(Token token) {
                        //保存token
                        Log.d(UserPresenter.this.getClass().getName(), token.getAccess_token());
                        MainApplication.instance().accountService().saveToken(token.getAccess_token());
                        return RepositoryUtils.transform(RepositoryUtils.getApiService().getUser(token.getAccess_token()));
                    }
                })
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mView.showUser(user);
                    }
                });
//        RepositoryUtils.getApiService().getToken(Constants.CLIENT_ID, Constants.CLIENT_SCERET, code)
////                .enqueue(new Callback<Token>() {
////                    @Override
////                    public void onResponse(Call<Token> call, Response<Token> response) {
////                        Log.d(UserPresenter.this.getClass().getName(), response.message());
////                        Token token = response.body();
////
////                        if (token != null) {
////                            Log.d(UserPresenter.this.getClass().getName(), token.getAccess_token());
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(Call<Token> call, Throwable t) {
////                        Log.d(UserPresenter.this.getClass().getName(), t.getMessage());
////
////                    }
////                });
//                .subscribe(new Action1<Token>() {
//                    @Override
//                    public void call(Token token) {
//                        Log.d(UserPresenter.this.getClass().getName(), token.getAccess_token());
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.d(UserPresenter.this.getClass().getName(), throwable.getMessage());
//                    }
//                });
//                .flatMap(new Func1<Token, Observable<User>>() {
//                    @Override
//                    public Observable<User> call(Token token) {
//                        //保存token
//                        Log.d(UserPresenter.this.getClass().getName(), token.getAccess_token());
//                        MainApplication.instance().accountService().saveToken(token.getAccess_token());
//                        return RepositoryUtils.getApiService().getUser(token.getAccess_token());
//                    }
//
//                })
//                .subscribe(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        mView.showUser(user);
//                    }
//                });
    }

    @Override
    public void getUserById(int userId) {

    }

    @Override
    public void getProfileUser() {

    }


}
