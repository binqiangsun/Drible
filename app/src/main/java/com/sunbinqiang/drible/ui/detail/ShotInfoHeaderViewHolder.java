package com.sunbinqiang.drible.ui.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.databinding.ShotInfoHeadViewBinding;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.ui.user.UserInfoActivity;

/**
 * Created by sunbinqiang on 6/18/16.
 */

public class ShotInfoHeaderViewHolder extends BaseViewHolder {

    ShotInfoHeadViewBinding binding;
    private Context mContext;
    private Shot mShot;

    public ShotInfoHeaderViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.shot_info_head_view);
        binding = DataBindingUtil.bind(itemView);
        mContext = context;
    }

    public void setShot(Shot shot){
        mShot = shot;
        binding.setShot(shot);
        binding.setShot(mShot);
        if(!TextUtils.isEmpty(mShot.getDescription())) {
            binding.shotTextTv.setText(Html.fromHtml(mShot.getDescription()));
        }
        binding.setClickHandlers(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.author_drawee_view :
                        UserInfoActivity.startUserInfoActivity(getContext(), mShot.getUser().getId());
                }
            }
        });
        binding.executePendingBindings();
    }
}
