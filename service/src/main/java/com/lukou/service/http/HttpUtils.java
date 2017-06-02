package com.lukou.service.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lukou.service.LibApplication;


/**
 * Created by sunbinqiang on 9/13/16.
 */

public class HttpUtils {

    public static boolean isNetworkConnected() {
        Context context = LibApplication.instance().getApplicationContext();
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
                }
            }
        return false;
        }


}
