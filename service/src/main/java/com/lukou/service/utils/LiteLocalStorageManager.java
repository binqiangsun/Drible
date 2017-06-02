package com.lukou.service.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.lukou.service.LibApplication;

import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Xu
 *         一个小型本地数据存储器，适合储存一些小的数据量
 *         支持:int long String float boolean Set<String>
 */

public class LiteLocalStorageManager {
    private SharedPreferences preference;

    /**
     * 单例
     */
    private static class SingletonHolder {
        private static final LiteLocalStorageManager INSTANCE = new LiteLocalStorageManager(LibApplication.instance());
    }

    private LiteLocalStorageManager(Context context) {
        this.preference = context.getSharedPreferences("lite_local_storage", MODE_PRIVATE);
    }

    public static final LiteLocalStorageManager instance() {
        return SingletonHolder.INSTANCE;
    }

    public void putString(String key, @Nullable String value) {
        preference.edit().putString(key, value).apply();
    }

    public void putStringSync(String key, @Nullable String value) {
        preference.edit().putString(key, value).commit();
    }

    public void putStringSet(String key, @Nullable Set<String> values) {
        preference.edit().putStringSet(key, values).apply();
    }

    public void putInt(String key, int value) {
        preference.edit().putInt(key, value).apply();
    }

    public void putIntSync(String key, int value) {
        preference.edit().putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        preference.edit().putLong(key, value).apply();
    }

    public void putFloat(String key, float value) {
        preference.edit().putFloat(key, value).apply();
    }

    public void putFloatSync(String key, float value) {
        preference.edit().putFloat(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        preference.edit().putBoolean(key, value).apply();
    }

    public void putBooleanSync(String key, boolean value) {
        preference.edit().putBoolean(key, value).commit();
    }

    public void remove(String key) {
        preference.edit().remove(key).apply();
    }

    public void clear() {
        preference.edit().clear().apply();
    }

    public String getString(String key, @Nullable String defValue) {
        return preference.getString(key, defValue);
    }

    public long getLong(String key, @Nullable long defValue) {
        return preference.getLong(key, defValue);
    }

    public boolean getBoolean(String key, @Nullable boolean defValue) {
        return preference.getBoolean(key, defValue);
    }

    public float getFloat(String key, @Nullable float defValue) {
        return preference.getFloat(key, defValue);
    }

    public int getInt(String key, @Nullable int defValue) {
        return preference.getInt(key, defValue);
    }

    public Set<String> getStringSet(String key, @Nullable Set<String> defValue) {
        return preference.getStringSet(key, defValue);
    }

}
