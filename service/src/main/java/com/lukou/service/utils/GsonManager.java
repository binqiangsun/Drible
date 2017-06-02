package com.lukou.service.utils;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

/**
 * @author Xu
 */

public class GsonManager {

    private final Gson gson;

    //内部类，在装载该内部类时才会去创建单利对象
    private static class SingletonHolder {
        public static GsonManager instance = new GsonManager();
    }

    private GsonManager() {
        gson = new GsonBuilder().setFieldNamingStrategy(new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                if (f.getName().equals("_package")) {
                    return "package";
                }
                return f.getName();
            }
        }).create();
    }

    public static final GsonManager instance() {
        return SingletonHolder.instance;
    }

    /**
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public String toJson(Object src) {
        return GsonManager.instance().gson.toJson(src);
    }

}
