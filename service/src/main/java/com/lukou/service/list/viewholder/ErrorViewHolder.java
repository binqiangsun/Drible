package com.lukou.service.list.viewholder;

import android.content.Context;
import android.view.View;

import com.lukou.service.list.view.LoadingErrorView;
import com.lukou.service.list.view.RetryLoadListener;



/**
 * @author Xu
 */
public class ErrorViewHolder extends BaseViewHolder {

    public LoadingErrorView errorView;

    private ErrorViewHolder(View itemView) {
        super(itemView);
        errorView = (LoadingErrorView) itemView;
    }

    public static ErrorViewHolder create(Context context) {
        LoadingErrorView errorView = new LoadingErrorView(context);
        return new ErrorViewHolder(errorView);
    }

    public void setRetry(RetryLoadListener retryCallback) {
        errorView.setRetryCallBack(retryCallback);
    }

    public void setRetry(String errorMsg, RetryLoadListener retryCallback) {
        errorView.setErrorToastMessage(errorMsg);
        setRetry(retryCallback);
    }

}