package com.sunbinqiang.drible.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.lukou.service.http.Resource;
import com.sunbinqiang.drible.bean.Comment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunbinqiang on 15/06/2017.
 */

public class CommentListRepository {

    private static CommentListRepository instance;

    private CommentListRepository() {
    }

    public static CommentListRepository getInstance(){
        if (instance == null) {
            synchronized (CommentListRepository.class) {
                if(instance == null) {
                    instance = new CommentListRepository();
                }
            }
        }
        return instance;
    }

    /**
     * @param page
     * @return
     */
    public void getComments(int shotId, final int page, MutableLiveData<Resource<Comment[]>> liveData){
        getCommentsFromNet(shotId, page, liveData);
    }

    private void getCommentsFromNet(int shotId, int page, final MutableLiveData<Resource<Comment[]>> liveData){
        Log.d("shotRepo", "get shots from net");
        RepositoryUtils.getApiService().getComments(shotId, page).enqueue(new Callback<Comment[]>() {
            @Override
            public void onResponse(Call<Comment[]> call, Response<Comment[]> response) {
                // error case is left out for brevity
                Comment[] comments = response.body();
                if (response.isSuccessful() && comments != null) {
                    liveData.setValue(Resource.success(comments));
                } else {
                    liveData.setValue(Resource.error("data is null", (Comment[])null));
                }
            }

            @Override
            public void onFailure(Call<Comment[]> call, Throwable t) {
                liveData.setValue(Resource.error(t.getMessage(), (Comment[])null));
            }
        });
    }

}
