package com.lukou.service.list.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lukou.service.R;
import com.lukou.service.databinding.LoadingItemBinding;

/**
 * @author Xu
 */
public class LoadingItemView extends FrameLayout implements View.OnAttachStateChangeListener {

    private static final int ANIMATION_INTERVAL = 500;

    private LoadingItemBinding binding;

    public LoadingItemView(Context context) {
        this(context, null);
    }

    public LoadingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.loading_item,this,true);
        LayoutParams lps = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lps);
        addOnAttachStateChangeListener(this);
    }

    private Runnable loadingAnimation = new Runnable() {
        @Override
        public void run() {
            postDelayed(this, ANIMATION_INTERVAL);
            binding.setShowPointNum((binding.getShowPointNum() + 1) % 4);
        }
    };

    @Override
    public void onViewAttachedToWindow(View v) {
        v.post(loadingAnimation);
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        v.removeCallbacks(loadingAnimation);
    }

}
