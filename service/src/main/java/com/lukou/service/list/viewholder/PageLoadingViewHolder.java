package com.lukou.service.list.viewholder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukou.service.R;


/**
 * @author Xu
 */
public class PageLoadingViewHolder extends BaseViewHolder {

    public PageLoadingViewHolder(Context context, ViewGroup parent, @LayoutRes int pageLoadingId) {
        this(LayoutInflater.from(context).inflate(pageLoadingId, parent, false));
    }

    public PageLoadingViewHolder(Context context, ViewGroup parent) {
        this(LayoutInflater.from(context).inflate(R.layout.page_loading_layout, parent, false));
    }

    public PageLoadingViewHolder(View itemView) {
        super(itemView);
    }
}

