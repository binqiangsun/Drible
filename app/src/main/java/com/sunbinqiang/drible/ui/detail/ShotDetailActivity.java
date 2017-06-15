package com.sunbinqiang.drible.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Priority;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.base.BaseActivity;
import com.sunbinqiang.drible.databinding.ActivityShotDetailLayoutBinding;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.viewmodel.ShotDetailViewModel;

/**
 * Created by sunbinqiang on 6/14/16.
 */
public class ShotDetailActivity extends BaseActivity {

    private int mShotId;
    private ActivityShotDetailLayoutBinding mBinding;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_detail_layout;
    }

    @Override
    protected void onBindView(View view) {
        mBinding = DataBindingUtil.bind(view);
    }

    @Override
    protected void initViews() {
        ShotDetailViewModel shotDetailViewModel = ViewModelProviders.of(this).get(ShotDetailViewModel.class);
        setTitle("");
        Shot shot = getIntent().getParcelableExtra("shot");
        mShotId = shot.getId();
        //
        mBinding.setShot(shot);
        mBinding.backdrop.setImageUrl(shot.getHdipImage(), shot.getNormImage(), Priority.NORMAL, 0, 0);

        //评论列表， shot详情
        ShotDetailAdapter shotDetailAdapter = new ShotDetailAdapter(this, shotDetailViewModel.getLiveData(), shotDetailViewModel);
        shotDetailAdapter.setShot(shot);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerView.setAdapter(shotDetailAdapter);
    }




}
