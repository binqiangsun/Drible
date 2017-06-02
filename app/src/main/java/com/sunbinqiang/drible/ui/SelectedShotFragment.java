package com.sunbinqiang.drible.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.databinding.ListFragmentBinding;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.viewmodel.ShotListViewModel;

import java.util.List;

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

        subscribeUi(viewModel);
    }

    private void subscribeUi(ShotListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getShots(0).observe(this, new Observer<List<Shot>>() {
            @Override
            public void onChanged(@Nullable List<Shot> shots) {
                if (shots != null && shots.size() > 0) {
                    Log.d("shotFragment", "data changed");
                    binding.loadingTv.setText(String.valueOf(shots.get(0).getDescription()));
                } else {
                    Log.d("shotFragment", "data changed null");
                    binding.loadingTv.setText("loading...");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        return binding.getRoot();
    }
}
