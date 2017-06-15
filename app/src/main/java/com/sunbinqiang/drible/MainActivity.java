package com.sunbinqiang.drible;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.sunbinqiang.drible.base.BaseActivity;
import com.sunbinqiang.drible.ui.selected.SelectedShotFragment;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("MainActivity", "create");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.d("MainActivity", "start");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity", "stop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "destroy");
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        Log.d("MainActivity", "lowMemory");
        super.onLowMemory();
    }

    @Override
    protected void onPause() {
        Log.d("MainActivity", "pause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("MainActivity", "resume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d("MainActivity", "restart");
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d("MainActivity", "onsaveinstance");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("MainActivity", "onRestoreInstance");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
