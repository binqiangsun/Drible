package com.sunbinqiang.drible.ui.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.lukou.service.account.bean.User;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.base.BaseActivity;
import com.sunbinqiang.drible.databinding.UserInfoLayoutBinding;


/**
 * Created by sunbinqiang on 8/7/16.
 */

public class UserInfoActivity extends BaseActivity implements UserConstract.View {
    private UserInfoLayoutBinding mBinding;
    private UserConstract.Presenter mPresenter;

    //其他人个人主页
    public static void startUserInfoActivity(Context context, int userId) {
        Intent intent = new Intent(context, UserInfoActivity.class);
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
        mPresenter.setTabViewPager(getSupportFragmentManager(), mBinding.userTabs, mBinding.userViewPager);
    }

    @Override
    public void startOtherActivity(Intent intent) {
        startActivity(intent);
    }



}
