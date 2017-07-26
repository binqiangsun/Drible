package com.sunbinqiang.drible.ui.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.ViewGroup;

import com.lukou.service.account.bean.User;
import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.base.BaseListFragment;
import com.sunbinqiang.drible.databinding.UserViewHolderBinding;
import com.sunbinqiang.drible.repository.RepositoryUtils;

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


        return new UserAdapter(type, userId);
    }

    private static class UserAdapter extends ListRecyclerViewAdapter<User>{

        private int mType;
        private int mUserId;

        public UserAdapter(int type, int userId) {
            mType = type;
            mUserId = userId;
        }

        @Override
        protected Observable<User[]> request(int nextId) {
            switch (mType) {
                case TYPE_FOLLOWER:
                    return RepositoryUtils.getApiService().getFollowers(mUserId, String.valueOf(nextId));
                case TYPE_FOLLOWING:
                    return RepositoryUtils.getApiService().getFollowings(mUserId, String.valueOf(nextId));
                default:
                    return null;
            }
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
