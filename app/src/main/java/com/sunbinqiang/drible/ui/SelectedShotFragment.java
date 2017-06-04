package com.sunbinqiang.drible.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukou.service.list.adapter.BaseListRecyclerViewAdapter;
import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.databinding.ListFragmentBinding;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.viewmodel.ShotListViewModel;

/**
 * Created by sunbinqiang on 01/06/2017.
 */

public class SelectedShotFragment extends LifecycleFragment {

    private ShotListViewModel viewModel;
    private ListFragmentBinding binding;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ShotListViewModel.class);

        initView(viewModel);
    }

    private void initView(ShotListViewModel viewModel) {
        ShotAdapter shotAdapter = new ShotAdapter(this, viewModel);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(BaseListRecyclerViewAdapter.getSpanSizeLookup(shotAdapter));
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(shotAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        return binding.getRoot();
    }


    private static class ShotAdapter extends ListRecyclerViewAdapter<Shot> {

        private ShotListViewModel viewModel;

        public ShotAdapter(LifecycleOwner lifecycleOwner, ShotListViewModel viewModel) {
            super(lifecycleOwner);
            this.viewModel = viewModel;
        }

        @Override
        protected LiveData<Shot[]> request(int nextId) {
            return viewModel.getShots(nextId);
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
