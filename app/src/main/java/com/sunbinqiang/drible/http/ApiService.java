package com.sunbinqiang.drible.http;

import com.lukou.service.account.bean.User;
import com.sunbinqiang.drible.bean.Comment;
import com.sunbinqiang.drible.bean.ShotResult;
import com.sunbinqiang.drible.bean.Token;
import com.sunbinqiang.drible.db.entity.Shot;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("https://dribbble.com/oauth/token")
    Observable<Token> getToken(@Query("client_id") String clientId,
                        @Query("client_secret") String clientSecret,
                        @Query("code") String code);

    @GET("user")
    Observable<User> getUser(@Query("access_token") String token);

    @GET("users/{id}")
    Observable<User> getUser(@Path("id") int userId);

    @GET("users/{id}/followers")
    Observable<User[]> getFollowers(@Path("id") int id,
                                    @Query("page") String page);
    @GET("users/{id}/following")
    Observable<User[]> getFollowings(@Path("id") int id,
                                     @Query("page") String page);

    @GET("users/{id}/likes")
    Observable<ShotResult[]> getLikes(@Path("id") int id,
                                      @Query("page") String page);
    @GET("users/{id}/shots")
    Observable<Shot[]> getShots(@Path("id") int id,
                                @Query("page") String page);




}
