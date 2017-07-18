package com.sunbinqiang.drible.ui.user;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.ViewGroup;

import com.lukou.service.account.bean.User;
import com.lukou.service.http.Resource;
import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.base.BaseListFragment;
import com.sunbinqiang.drible.databinding.UserViewHolderBinding;

import rx.Observable;


/**
 * Created by sunbinqiang on 26/11/2016.
 * 用户列表
 * 类别： 1， 关注的用户列表； 2， 粉丝列表
 */

public class UserListFragment extends BaseListFragment<User> {

    public static final int TYPE_FOLLOWER = 1;
    public static final int TYPE_FOLLOWING = 2;

    //实例化, 传递参数
    public static UserListFragment newInstance(int type, int userId){
        UserListFragment userListFragment = new UserListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("userId", userId);
        userListFragment.setArguments(bundle);
        return userListFragment;
    }


    @Override
    protected ListRecyclerViewAdapter<User> getAdapter() {
        int type = getArguments().getInt("type");
        int userId = getArguments().getInt("userId");

        UserListViewModel userListViewModel = new UserListViewModel();
        return null;
    }

    private static class UserAdapter extends ListRecyclerViewAdapter<User>{

        private int mType;
        private int mUserId;

        public UserAdapter(LifecycleOwner lifecycleOwner, MutableLiveData<Resource<User[]>> liveData) {
            super(lifecycleOwner, liveData);
        }

        @Override
        protected Observable<User[]> request(int nextId) {
            return null;
        }

        @Override
        protected BaseViewHolder onCreateItemViewHolder(Context context, ViewGroup parent, int viewType) {
            return new UserViewHolder(parent.getContext(), parent);
        }

        @Override
        protected void onBindItemViewHolder(BaseViewHolder holder, int position) {
            ((UserViewHolder)holder).setUser(getList().get(position));
        }
    }

    private static class UserViewHolder extends BaseViewHolder{

        private UserViewHolderBinding mBinding;

        public UserViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.user_view_holder);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setUser(User user){
            mBinding.setUser(user);
        }

    }
}
