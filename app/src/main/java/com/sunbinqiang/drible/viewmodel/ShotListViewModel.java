package com.sunbinqiang.drible.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.lukou.service.http.Resource;
import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.repository.ShotListRepository;
import com.sunbinqiang.drible.ui.SelectedViewHolder;

/**
 * Created by sunbinqiang on 01/06/2017.
 */

public class ShotListViewModel extends ViewModel {

    private ShotListRepository shotRepository;
    MutableLiveData<Resource<Shot[]>> shotsLiveData; //viewmodel会重用该对象
    ShotAdapter shotAdapter;


    public ShotListViewModel() {
        this.shotRepository = ShotListRepository.getInstance();
    }

    public void getShots(int page, MutableLiveData<Resource<Shot[]>> liveData) {
        shotRepository.getSelectedShots(page, liveData);
    }

    public ShotAdapter getShotAdapter(LifecycleOwner lifecycleOwner) {
        if (shotAdapter == null) {
            Log.d("shotViewmodel", "adapter is null");
            shotAdapter = new ShotAdapter(lifecycleOwner, this);
        }
        return shotAdapter;
    }


    private static class ShotAdapter extends ListRecyclerViewAdapter<Shot> {

        private ShotListViewModel viewModel;

        public ShotAdapter(LifecycleOwner lifecycleOwner, ShotListViewModel viewModel) {
            super(lifecycleOwner);
            this.viewModel = viewModel;
        }

        @Override
        protected void request(int nextId, MutableLiveData<Resource<Shot[]>> liveData) {
            Log.d("shotAdapter", "request next page" + nextId);
            viewModel.getShots(nextId, liveData);
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
