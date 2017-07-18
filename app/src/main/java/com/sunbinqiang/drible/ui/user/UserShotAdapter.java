package com.sunbinqiang.drible.ui.user;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.view.ViewGroup;

import com.lukou.service.http.Resource;
import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.ui.selected.SelectedViewHolder;

import rx.Observable;

/**
 * Created by sunbinqiang on 4/10/16.
 */
public class UserShotAdapter extends ListRecyclerViewAdapter<Shot> {

    public UserShotAdapter(LifecycleOwner lifecycleOwner, MutableLiveData<Resource<Shot[]>> liveData) {
        super(lifecycleOwner, liveData);
    }

    @Override
    protected Observable<Shot[]> request(int nextId) {
        return null;
    }

    @Override
    protected BaseViewHolder onCreateItemViewHolder(Context context, ViewGroup parent, int viewType) {
        return new SelectedViewHolder(parent.getContext(), parent);
    }

    @Override
    protected void onBindItemViewHolder(BaseViewHolder holder, int position) {
        ((SelectedViewHolder)holder).setShots(getList().get(position));
    }
}
