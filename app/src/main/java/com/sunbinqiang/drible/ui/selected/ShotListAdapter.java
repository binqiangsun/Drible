package com.sunbinqiang.drible.ui.selected;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.lukou.service.http.Resource;
import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.viewmodel.ShotListViewModel;

/**
 * Created by sunbinqiang on 15/06/2017.
 */

public class ShotListAdapter extends ListRecyclerViewAdapter<Shot, ShotListViewModel> {


    public ShotListAdapter(LifecycleOwner lifecycleOwner,
                           MutableLiveData<Resource<Shot[]>> shotsLiveData, ShotListViewModel viewModel) {
        super(lifecycleOwner, shotsLiveData, viewModel);
    }

    @Override
    protected void request(int nextId) {
        Log.d("shotAdapter", "request next page" + nextId);
        viewModel.getNextShots(nextId);
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
