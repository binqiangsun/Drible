package com.sunbinqiang.drible;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sunbinqiang.drible.base.BaseActivity;
import com.sunbinqiang.drible.ui.SelectedShotFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onBindView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setTitle("Drible");
        SelectedShotFragment fragment = new SelectedShotFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commitAllowingStateLoss();
    }
}
