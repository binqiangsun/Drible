package com.lukou.service.list.viewholder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Xu
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public BaseViewHolder(Context context, ViewGroup parent, int layoutId) {
        this(context, parent, layoutId, false);
    }

    public BaseViewHolder(Context context, ViewGroup parent, int layoutId, boolean attachToRoot) {
        super(LayoutInflater.from(context).inflate(layoutId, parent, attachToRoot));
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public Resources getResources() {
        return itemView.getResources();
    }

    public LayoutInflater getLayoutInflate() {
        return LayoutInflater.from(itemView.getContext());
    }

    public View findViewById(int viewId) {
        return itemView.findViewById(viewId);
    }

    public void startActivity(Intent intent) {
        itemView.getContext().startActivity(intent);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }

}
