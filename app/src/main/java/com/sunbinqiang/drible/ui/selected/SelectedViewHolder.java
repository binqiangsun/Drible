package com.sunbinqiang.drible.ui.selected;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.databinding.SelectedRecyclerViewBinding;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.ui.detail.ShotDetailActivity;


/**
 * Created by sunbinqiang on 16/2/28.
 */
public class SelectedViewHolder extends BaseViewHolder implements View.OnClickListener {

    SelectedRecyclerViewBinding binding;
    private Context mContext;
    private Shot mShot;

    public SelectedViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.selected_recycler_view);
        binding = DataBindingUtil.bind(itemView);
        mContext = context;
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInfo();
            }
        });
    }

    public void setShots(Shot shot){
        mShot = shot;
        binding.setShot(shot);
        binding.executePendingBindings();
    }


    @Override
    public void onClick(View v) {
        startInfo();
    }

    private void startInfo(){
        //TODO start info activity
        Intent intent = new Intent(getContext(), ShotDetailActivity.class);
        intent.putExtra("shot", mShot);
        getContext().startActivity(intent);
    }
}
