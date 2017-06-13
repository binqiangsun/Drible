package com.sunbinqiang.drible.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.sunbinqiang.drible.R;


/**
 * Created by sunbinqiang on 16/2/26.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(getLayoutId(), null, false);
        setContentView(view);
        onBindView(view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        initViews();
        setViewListener();
    }

    abstract protected void onBindView(View view);

    /**
     * set layout Id
     * @return
     */
    abstract protected int getLayoutId();

    /**
     * init views and set click listeners
     */
    abstract protected void initViews();

    protected void setViewListener(){
        return;
    }

    protected void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void setTitle(String title) {
        toolbar.setTitle(title);
    }
}
