package com.sunbinqiang.drible.ui.detail;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.bumptech.glide.Priority;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.base.BaseActivity;
import com.sunbinqiang.drible.databinding.ActivityShotDetailLayoutBinding;
import com.sunbinqiang.drible.db.entity.Shot;

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
        setTitle("");
        Shot shot = getIntent().getParcelableExtra("shot");
        mShotId = shot.getId();
        mBinding.setShot(shot);
        mBinding.backdrop.setImageUrl(shot.getHdipImage(), shot.getNormImage(), Priority.NORMAL, 0, 0);
    }




}
