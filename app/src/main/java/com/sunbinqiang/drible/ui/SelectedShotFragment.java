package com.sunbinqiang.drible.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukou.service.list.adapter.BaseListRecyclerViewAdapter;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.databinding.ListFragmentBinding;
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

    }

    private void initView(ShotListViewModel viewModel) {
        Log.d("shotFragment", "init view");
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(BaseListRecyclerViewAdapter.getSpanSizeLookup(viewModel.getShotAdapter(this)));
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(viewModel.getShotAdapter(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(ShotListViewModel.class);

        initView(viewModel);
        return binding.getRoot();
    }



}
