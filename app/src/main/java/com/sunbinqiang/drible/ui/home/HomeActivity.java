package com.sunbinqiang.drible.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.base.BaseActivity;
import com.sunbinqiang.drible.databinding.HomeActivityBinding;
import com.sunbinqiang.drible.ui.selected.SelectedShotFragment;
import com.sunbinqiang.drible.ui.user.UserInfoActivity;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    HomeActivityBinding mBinding;

    private static final int gallery_image_res[] =
            {
                R.drawable.ic_wall_1, R.drawable.ic_wall_2, R.drawable.ic_wall_3, R.drawable.ic_wall_4};
    private ViewFlipper viewFlipper;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onBindView(View view) {
        mBinding = DataBindingUtil.bind(view);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if(uri != null) {
            Log.d("home", "initViews: " + uri.toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    protected void initViews() {
        Uri uri = getIntent().getData();
        if(uri != null) {
            Log.d("home", "initViews: " + uri.toString());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        viewFlipper = (ViewFlipper) navigationView.getHeaderView(0).findViewById(R.id.header_view_flipper);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //登录背景动画
        for (int gallery_image_re : gallery_image_res) {
            addResourceToFlipper(gallery_image_re);
        }

        //主页面
        SelectedShotFragment fragment = new SelectedShotFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commitAllowingStateLoss();

        //点击登录
        navigationView.getHeaderView(0).findViewById(R.id.author_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.startProfileActivity(HomeActivity.this);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.author_iv) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addResourceToFlipper(int res){
        ImageView image = new ImageView(getApplicationContext());
        image.setBackgroundResource(res);
        viewFlipper.addView(image);
    }
}
