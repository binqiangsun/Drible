package com.lukou.service;

import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public abstract class LibApplication extends Application {

    private DisplayMetrics mDisplayMetrics;
    private static LibApplication instance;
    private Handler mainLooperHandler;

	@Override
	public void onCreate() {
        instance = this;
		super.onCreate();
        mainLooperHandler = new Handler(getMainLooper());
	}

    public SharedPreferences preferences() {
        return getSharedPreferences(getPackageName(), MODE_PRIVATE);
    }

    public DownloadManager downloadManager(){
        return (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    public void sendLocalBroadcast(Intent intent) {
        if (intent == null) {
            return;
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void registerLocalReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (receiver == null) {
            return;
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    public void unregisterLocalReceiver(BroadcastReceiver receiver) {
        if (receiver == null) {
            return;
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public DisplayMetrics getDisplayMetrics() {
        if (mDisplayMetrics == null) {
            DisplayMetrics metric = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metric);
            mDisplayMetrics = metric;
        }
        return mDisplayMetrics;
    }

    public static LibApplication instance() {
        if (instance == null) {
            throw new RuntimeException("Application is not initialized");
        }
        return instance;
    }

    public Handler mainLooperHandler() {
        return mainLooperHandler;
    }
}