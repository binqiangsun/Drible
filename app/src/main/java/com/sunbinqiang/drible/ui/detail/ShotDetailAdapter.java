package com.sunbinqiang.drible.ui.detail;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.view.ViewGroup;

import com.lukou.service.http.Resource;
import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.sunbinqiang.drible.bean.Comment;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.viewmodel.ShotDetailViewModel;

/**
 * Created by sunbinqiang on 15/06/2017.
 */

public class ShotDetailAdapter extends ListRecyclerViewAdapter<Comment, ShotDetailViewModel> {

    private Shot shot;

    public ShotDetailAdapter(LifecycleOwner lifecycleOwner,
                             MutableLiveData<Resource<Comment[]>> liveData, ShotDetailViewModel commentViewModel) {
        super(lifecycleOwner, liveData, commentViewModel);
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }


    @Override
    protected void request(int nextId) {
        viewModel.getNextComments(this.shot.getId(), nextId);
    }

    @Override
    protected BaseViewHolder onCreateItemViewHolder(Context context, ViewGroup parent, int viewType) {
        return new CommentViewholder(parent.getContext(), parent);
    }

    @Override
    protected void onBindItemViewHolder(BaseViewHolder holder, int position) {
        ((CommentViewholder)holder).setComment(getList().get(position));
    }

    @Override
    protected BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent, int position) {
        return new ShotInfoHeaderViewHolder(parent.getContext(), parent);
    }

    @Override
    protected void onBindHeaderViewHolder(BaseViewHolder holder, int position) {
        ((ShotInfoHeaderViewHolder)holder).setShot(this.shot);
    }

    @Override
    protected int getHeaderViewCount() {
        return shot == null ? 0 : 1;
    }
}
