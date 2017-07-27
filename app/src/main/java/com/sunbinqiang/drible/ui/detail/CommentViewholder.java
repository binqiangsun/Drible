package com.sunbinqiang.drible.ui.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.bean.Comment;
import com.sunbinqiang.drible.databinding.CommentViewBinding;
import com.sunbinqiang.drible.ui.user.UserInfoActivity;

/**
 * Created by sunbinqiang on 6/18/16.
 */

public class CommentViewholder extends BaseViewHolder {

    CommentViewBinding binding;
    private Context mContext;
    private Comment mComment;

    public CommentViewholder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.comment_view);
        binding = DataBindingUtil.bind(itemView);
        mContext = context;
        binding.setClickHandlers(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.author_drawee_view :
                        UserInfoActivity.startUserInfoActivity(getContext(), mComment.getUser().getId());
                }
            }
        });
    }

    public void setComment(Comment comment){
        mComment = comment;
        binding.setComment(comment);
    }
}
