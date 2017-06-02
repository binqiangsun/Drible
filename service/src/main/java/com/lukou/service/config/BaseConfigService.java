package com.lukou.service.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import com.lukou.service.bean.Config;

/**
 * Created by wangzhicheng on 2017/3/23.
 * 获取和保存一些基本的config信息
 */

public abstract class BaseConfigService implements ConfigService{
    protected Context context;

    protected SharedPreferences prefs;

    public BaseConfigService(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    @Override
    public Config config() {
        String configJsonString = prefs.getString("config", null);
        if (TextUtils.isEmpty(configJsonString))
            return null;
        Gson gson = new Gson();
        return gson.fromJson(configJsonString,Config.class);
    }

    @Override
    public void update(Config config) {
        Gson gson = new Gson();
        prefs.edit()
                .putString("config", gson.toJson(config))
                .apply();
    }
}
