package com.sunbinqiang.drible.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.base.BaseActivity;
import com.sunbinqiang.drible.databinding.WebLayoutBinding;


/**
 * Created by sunbinqiang on 7/27/16.
 */

public class LoginWebActivity extends BaseActivity {

    WebLayoutBinding mBinding;

    @Override
    protected void onBindView(View view) {
        mBinding = DataBindingUtil.bind(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.web_layout;
    }

    @Override
    protected void initViews() {
        String url = getIntent().getStringExtra("url");
        if(url.contains("auth")){
            Uri uri = Uri.parse(url);
            url = uri.buildUpon().appendQueryParameter("client_id", "4bb696924129982493144fe6c11560052410112e681aa8c931226a437965521d")
                    .appendQueryParameter("redirect_uri", "drible://user")
                    .appendQueryParameter("scope", "public write comment upload")
                    .appendQueryParameter("state", "dribbblelogin")
                    .build().toString();
            Log.e("web", "url:"+url);
        }
        mBinding.web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBinding.web.loadUrl(url);
        mBinding.web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.toLowerCase().startsWith("drible://")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    LoginWebActivity.this.startActivity(intent);
                    LoginWebActivity.this.finish();
                    return false;
                }else {
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String webViewTitle = null;

                if (mBinding.web != null) {
                    webViewTitle = mBinding.web.getTitle();
                }
                if (!TextUtils.isEmpty(webViewTitle)) {
                    setTitle(webViewTitle);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        mBinding.web.setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mBinding.progressBar.setVisibility(View.GONE);
            } else {
                if (mBinding.progressBar.getVisibility() == View.GONE)
                    mBinding.progressBar.setVisibility(View.VISIBLE);
                mBinding.progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

    }

}
