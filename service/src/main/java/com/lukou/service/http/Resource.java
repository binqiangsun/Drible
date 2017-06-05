package com.lukou.service.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sunbinqiang on 05/06/2017.
 */

public class Resource<T> {

    public static final int LOADING = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;

    public final int status;
    @Nullable
    public final T data;
    @Nullable public final String message;
    private Resource(int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
