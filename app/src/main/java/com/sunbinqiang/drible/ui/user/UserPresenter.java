package com.sunbinqiang.drible.ui.user;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.lukou.service.account.bean.User;
import com.sunbinqiang.drible.MainApplication;
import com.sunbinqiang.drible.bean.Token;
import com.sunbinqiang.drible.repository.RepositoryUtils;
import com.sunbinqiang.drible.ui.user.UserConstract.Presenter;
import com.sunbinqiang.drible.util.Constants;
import com.sunbinqiang.drible.util.TypeUtils;
import com.sunbinqiang.drible.widget.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sunbinqiang on 05/07/2017.
 */

public class UserPresenter implements Presenter {

    private UserConstract.View mView;
    private User mUser;

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

        RepositoryUtils.getApiService().getToken(Constants.CLIENT_ID, Constants.CLIENT_SCERET, code)
                .flatMap(new Func1<Token, Observable<User>>() {
                    @Override
                    public Observable<User> call(Token token) {
                        //保存token
                        Log.d(UserPresenter.this.getClass().getName(), token.getAccess_token());
                        MainApplication.instance().accountService().saveToken(token.getAccess_token());
                        return RepositoryUtils.transform(RepositoryUtils.getApiService().getUser(token.getAccess_token()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mUser = user;
                        MainApplication.instance().accountService().saveUser(user);
                        mView.showUser(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void getUserById(int userId) {
        RepositoryUtils.getApiService().getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mUser = user;
                        mView.showUser(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    @Override
    public void getProfileUser() {
        //
        if (MainApplication.instance().accountService().isLogin() &&
                MainApplication.instance().accountService().user() != null) {
            mUser = MainApplication.instance().accountService().user();
            mView.showUser(mUser);
        } else {
            login();
        }
    }

    private void login() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("drible://login"));
        intent.putExtra("url", "https://dribbble.com/oauth/authorize");
        mView.startOtherActivity(intent);
    }

    @Override
    public void setTabViewPager(FragmentManager fragmentManager, TabLayout tabLayout, ViewPager viewPager){

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(fragmentManager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_SHOTS, mUser.getId()));
        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_LIKES, mUser.getId()));

        fragmentList.add(UserListFragment.newInstance(UserListFragment.TYPE_FOLLOWER, mUser.getId()));
        fragmentList.add(UserListFragment.newInstance(UserListFragment.TYPE_FOLLOWING, mUser.getId()));
        viewPageAdapter.setFragments(fragmentList);

        List<String> titles = new ArrayList<>();
        titles.add("ALL");
        titles.add("LIKES");
        titles.add("FOLLOWERS");
        titles.add("FOLLOWINGS");

        viewPageAdapter.setTitles(titles);

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }


}
