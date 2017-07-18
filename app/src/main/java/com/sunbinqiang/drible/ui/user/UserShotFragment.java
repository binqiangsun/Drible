package com.sunbinqiang.drible.ui.user;

import android.os.Bundle;

import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.sunbinqiang.drible.base.BaseListFragment;
import com.sunbinqiang.drible.db.entity.Shot;

/**
 * Created by sunbinqiang on 16/2/27.
 * 用户页面的Shot列表
 */
public class UserShotFragment extends BaseListFragment<Shot> {

    //实例化, 传递参数
    public static UserShotFragment newInstance(int type, int userId){
        UserShotFragment selectedFragment = new UserShotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("userId", userId);
        selectedFragment.setArguments(bundle);
        return selectedFragment;
    }

    @Override
    protected ListRecyclerViewAdapter<Shot> getAdapter() {
        return null;
    }



}
