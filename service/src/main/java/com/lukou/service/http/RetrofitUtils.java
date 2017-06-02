package com.lukou.service.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import static rx.schedulers.Schedulers.io;

/**
 * Created by quqiuzhu on 9/10/16.
 */

public class RetrofitUtils {


    private volatile static Retrofit retrofit;

    private final HttpResultFunc<Object> func = new HttpResultFunc<>();
    private final ErrorHandlerFunc errorFunc = new ErrorHandlerFunc();
    private final Transformer transformer = new RetrofitTransformer();

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (retrofit == null) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    retrofit = new Retrofit.Builder()
                            .client(HttpClient.get())
                            .baseUrl(HttpConstant.API_BASE)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    protected <T> Observable.Transformer<HttpResult<T>, T> lifts() {
        return (Observable.Transformer<HttpResult<T>, T>) transformer;
    }

    private class RetrofitTransformer implements Transformer {

        @Override
        public Object call(Object o) {
            Observable observable = ((Observable) o);
            return observable.subscribeOn(io())
                    .unsubscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(errorFunc)
                    .map(func);
        }
    }

    private class ErrorHandlerFunc implements Func1<Exception, Object> {
        @Override
        public Object call(Exception e) {
            throw new HttpException(400, e.getMessage());
        }
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult == null) {
                throw new HttpException(400, "网络错误");
            } else if (!httpResult.isSuccess()) {
                String msg = httpResult.msg != null ? httpResult.msg : "未知错误";
                throw new HttpException(httpResult.code, msg);
            } else
                return httpResult.data;
        }
    }


    protected RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    protected RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }
}
