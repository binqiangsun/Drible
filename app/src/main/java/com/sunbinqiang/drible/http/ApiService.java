package com.sunbinqiang.drible.http;

import com.sunbinqiang.drible.bean.Comment;
import com.sunbinqiang.drible.db.entity.Shot;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by sunbinqiang on 26/05/2017.
 */

public interface ApiService {



    @GET("shots")
    Observable<Shot[]> getShots(@Query("page") String page,
                                @QueryMap Map<String, String> queryMap);

    @GET("shots")
    Call<Shot[]> getShots(@Query("page") int page);

    @GET("shots/{id}/comments")
    Call<Comment[]> getComments(@Path("id") int id,
                                @Query("page") int page);


}
