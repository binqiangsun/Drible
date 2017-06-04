package com.lukou.service.list.viewholder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Xu
 */
public class LoadingViewHolder extends BaseViewHolder {

    public LoadingViewHolder(Context context, ViewGroup parent, @LayoutRes int layoutId) {
        this(LayoutInflater.from(context).inflate(layoutId, parent, false));
    }

    public LoadingViewHolder(View itemView) {
        super(itemView);
    }
}

