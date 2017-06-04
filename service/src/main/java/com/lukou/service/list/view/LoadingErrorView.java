package com.lukou.service.list.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lukou.service.R;


/**
 * @author Xu
 */
public class LoadingErrorView extends LinearLayout implements
        View.OnClickListener {

    private TextView retryTextView;
    private RetryLoadListener callback;
    private String errorToastMessage;

    public LoadingErrorView(Context context) {
        this(context, null);
    }

    public LoadingErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.loading_error_view, this, true);
        LayoutParams lps = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lps);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_medium);
        setPadding(padding, padding, padding, padding);
        setShowDividers(SHOW_DIVIDER_MIDDLE);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        setOnClickListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        retryTextView = (TextView) findViewById(android.R.id.title);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        showErrorToast(errorToastMessage);
    }

    private void showErrorToast(String errorToastMessage) {
        Toast toast = Toast.makeText(getContext(), errorToastMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void setErrorToastMessage(String errorToastMessage) {
        this.errorToastMessage = errorToastMessage;
    }

    public void setRetryText(String retryText) {
        this.retryTextView.setText(retryText);
    }

    public void setRetryCallBack(RetryLoadListener callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        if (callback != null) {
            callback.retryLoad();
        }
    }

}
