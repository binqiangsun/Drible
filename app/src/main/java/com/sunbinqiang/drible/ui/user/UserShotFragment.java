package com.sunbinqiang.drible.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.base.BaseListFragment;
import com.sunbinqiang.drible.bean.ShotResult;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.repository.RepositoryUtils;
import com.sunbinqiang.drible.ui.selected.SelectedViewHolder;
import com.sunbinqiang.drible.util.TypeUtils;

import rx.Observable;
import rx.functions.Func1;


/**
 * Created by sunbinqiang on 16/2/27.
 * 用户页面的Shot列表
 */
public class UserShotFragment extends BaseListFragment<Shot> {

    //实例化, 传递参数
    public static UserShotFragment newInstance(int type, int userId) {
        UserShotFragment selectedFragment = new UserShotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("userId", userId);
        selectedFragment.setArguments(bundle);
        return selectedFragment;
    }

    @Override
    protected ListRecyclerViewAdapter<Shot> getAdapter() {
        int type = getArguments().getInt("type");
        int userId = getArguments().getInt("userId");
        return new UserShotAdapter(type, userId);
    }

    private static class UserShotAdapter extends ListRecyclerViewAdapter<Shot>{

        private int mType;
        private int mUserId;
        public UserShotAdapter(int type, int userId){
            mType = type;
            mUserId = userId;
        }

        @Override
        protected Observable<Shot[]> request(int nextId) {
            switch (mType){
                case TypeUtils.SHOT_LIKES:
                    return RepositoryUtils.getApiService().getLikes(mUserId, String.valueOf(nextId))
                            .map(new Func1<ShotResult[], Shot[]>() {
                                @Override
                                public Shot[] call(ShotResult[] shotResults) {
                                    Shot[] shots = new Shot[shotResults.length];
                                    for(int i = 0; i < shotResults.length; i ++){
                                        shots[i] = shotResults[i].getShot();
                                    }
                                    return shots;
                                }
                            });
                default:
                    return RepositoryUtils.getApiService().getShots(mUserId, String.valueOf(nextId));
            }
        }

        @Override
        protected BaseViewHolder onCreateItemViewHolder(Context context, ViewGroup parent, int viewType) {
            return new SelectedViewHolder(context, parent);
        }

        @Override
        protected void onBindItemViewHolder(BaseViewHolder holder, int position) {
            ((SelectedViewHolder)holder).setShots(getList().get(position));
        }
    }
}
