package com.lukou.service.http;


import com.lukou.service.LibApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by quqiuzhu on 9/10/16.
 * 1 放置公有头部等信息
 * 2 在此实现 Cache
 */

public class HttpClient {


    private static OkHttpClient client;

    public static OkHttpClient get() {
        if (client == null) {
            client = reBuild();
        }
        return client;
    }

    private static OkHttpClient reBuild() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder
                .addInterceptor(headerInterceptor)
//                .addNetworkInterceptor(cacheInterceptor)   //addInterceptor 缓存不生效
//                .addInterceptor(cacheInterceptor)
//                .cache(getHttpCache())
                .build();
    }

    /**
     * 拦截器,添加头部信息
     */
    private static Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .method(original.method(), original.body());
            try {
                builder.header("Authorization", "Bearer " + HttpConstant.ACCESS_TOKEN);
            }catch (Exception e){
                e.printStackTrace();
            }
            return chain.proceed(builder.build());
        }
    };

    /**
     * 拦截器,缓存
     * 1, 仅离线状态下缓存
     * 2, 在线状态下, 根据不同接口可以在ApiService接口中,添加Headers自定义缓存
     */
    private static Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!HttpUtils.isNetworkConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if(HttpUtils.isNetworkConnected()){
                //有网的时候读接口上的@Headers里的配置
                String cacheControl = request.cacheControl().toString();
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    private static Cache getHttpCache(){
        //设置缓存
        File httpCacheDirectory = new File(LibApplication.instance().getApplicationContext().getCacheDir(), "responses");
        return new Cache(httpCacheDirectory, 20 * 1024 * 1024);
    }


}
