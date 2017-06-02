package com.sunbinqiang.drible.repository;

import com.lukou.service.http.RetrofitUtils;
import com.sunbinqiang.drible.http.ApiService;

/**
 * Created by sunbinqiang on 31/05/2017.
 */

public class RepositoryUtils {

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
}
