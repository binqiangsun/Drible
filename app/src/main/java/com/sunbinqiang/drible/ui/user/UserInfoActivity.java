package com.sunbinqiang.drible.ui.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lukou.service.account.bean.User;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.base.BaseActivity;
import com.sunbinqiang.drible.databinding.UserInfoLayoutBinding;
import com.sunbinqiang.drible.widget.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sunbinqiang on 8/7/16.
 */

public class UserInfoActivity extends BaseActivity implements UserConstract.View {
    private UserInfoLayoutBinding mBinding;
    private UserConstract.Presenter mPresenter;

    //其他人个人主页
    public static void startUserInfoActivity(Context context, int userId) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra("other", true);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    //自己的个人主页
    public static void startProfileActivity(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onBindView(View view) {
        mBinding = DataBindingUtil.bind(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_info_layout;
    }

    @Override
    protected void initViews() {
        new UserPresenter(this);
        mPresenter.start(getIntent());
    }

    @Override
    public void setPresenter(UserConstract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showUser(User user) {
        mBinding.setUser(user);
        setTabViewPager(mBinding.userTabs, mBinding.userViewPager);
    }


//    public void getUser(String code){
//        //
//        ServiceFactory.toSubscribe(ApiFactory.getRequestService().getUser(CLIENT_ID, CLIENT_SECRET, code),
//                new Subscriber<Token>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(UserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNext(Token response) {
//                MainApplication.instance().accountService().updateToken(response.getAccess_token());
//                getUserInfo(response.getAccess_token());
//                showToast(response.getScope());
//            }
//        });
//    }
//
//    /**
//     * 获取用户信息 - 自己
//     */
//    private void getUserInfo(String token){
//        ServiceFactory.toSubscribe(ApiFactory.getRequestService().getUser(token),
//                new Subscriber<User>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(UserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(User user) {
//                        MainApplication.instance().accountService().updateUser(user);
//                        updateView(user);
//                    }
//                });
//    }
//
//    /**
//     * 获取用户信息 - 其他
//     */
//    private void getUserInfo(){
//        ServiceFactory.toSubscribe(ApiFactory.getRequestService().getUser(mUserId),
//                new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        updateView(user);
//                    }
//                });
//    }
//

//
    private void setTabViewPager(TabLayout tabLayout, ViewPager viewPager){

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        List<Fragment> fragmentList = new ArrayList<>();
//
//        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_SHOTS, mUser.getId()));
//        fragmentList.add(UserShotFragment.newInstance(TypeUtils.SHOT_LIKES, mUser.getId()));
//
//        fragmentList.add(UserListFragment.newInstance(UserListFragment.TYPE_FOLLOWER, mUser.getId()));
//        fragmentList.add(UserListFragment.newInstance(UserListFragment.TYPE_FOLLOWING, mUser.getId()));
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
