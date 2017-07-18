package com.sunbinqiang.drible.repository;

import android.arch.lifecycle.MutableLiveData;

import com.lukou.service.http.HttpException;
import com.lukou.service.http.Resource;
import com.lukou.service.http.RetrofitUtils;
import com.sunbinqiang.drible.http.ApiService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sunbinqiang on 31/05/2017.
 */

public class RepositoryUtils {

    public static final MutableLiveData LOADING_DATA = new MutableLiveData();
    {
        //noinspection unchecked
        LOADING_DATA.setValue(Resource.loading(null));
    }

    public static ExecutorService repository_executor = Executors.newSingleThreadExecutor();

    private static ApiService apiService;

    public static ApiService getApiService(){
        if (apiService == null) {
            synchronized (RepositoryUtils.class) {
                if (apiService == null) {
                    apiService = RetrofitUtils.getRetrofit().create(ApiService.class);
                }
            }
        }
        return apiService;
    }

    public static <T> Observable<T> transform(Observable<T> o) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, T>() {
                    @Override
                    public T call(Throwable throwable) {
                        throw new HttpException(400, throwable.getMessage());
                    }
                });
    }
}
