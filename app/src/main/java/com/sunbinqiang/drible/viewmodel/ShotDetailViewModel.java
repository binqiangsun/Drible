package com.sunbinqiang.drible.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.lukou.service.http.Resource;
import com.sunbinqiang.drible.bean.Comment;
import com.sunbinqiang.drible.repository.CommentListRepository;

/**
 * Created by sunbinqiang on 15/06/2017.
 */

public class ShotDetailViewModel extends ViewModel {

    private MutableLiveData<Resource<Comment[]>> liveData;

    public MutableLiveData<Resource<Comment[]>> getLiveData() {
        if (liveData == null) {
            Log.d("ShotDetailViewModel", "live data is not null");
            liveData = new MutableLiveData<>();
        }
        Log.d("ShotDetailViewModel", "live data is null");
        return liveData;
    }

    public void getNextComments(int shotId, int nextPage) {
        CommentListRepository.getInstance().getComments(shotId, nextPage, liveData);
    }
}
