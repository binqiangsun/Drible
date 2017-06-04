package com.lukou.service.list.viewholder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.lukou.service.R;
import com.lukou.service.databinding.PageErrorLayoutBinding;
import com.lukou.service.list.view.RetryLoadListener;


/**
 * @author Xu
 */
public class PageErrorViewHolder extends BaseViewHolder implements View.OnClickListener {

    public RetryLoadListener retryLoadListener;
    private PageErrorLayoutBinding binding;

    private PageErrorViewHolder(View itemView) {
        super(itemView);
    }

    public PageErrorViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.page_error_layout);
        binding = DataBindingUtil.bind(itemView);
        binding.getRoot().setOnClickListener(this);
    }

    public void setRetry(RetryLoadListener retryCallback) {
        this.retryLoadListener = retryCallback;
    }

    public void setRetryText(String errMsg){
        binding.retry.setText(errMsg);
    }

    @Override
    public void onClick(View v) {
        if (retryLoadListener != null) {
            retryLoadListener.retryLoad();
        }
    }
}