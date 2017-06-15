package com.sunbinqiang.drible.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lukou.service.http.Resource;
import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.repository.ShotListRepository;

/**
 * Created by sunbinqiang on 01/06/2017.
 */

public class ShotListViewModel extends ViewModel {

    private ShotListRepository shotRepository;
    private MutableLiveData<Resource<Shot[]>> shotsLiveData; //viewmodel会重用该对象


    public ShotListViewModel() {
        this.shotRepository = ShotListRepository.getInstance();
        shotsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<Shot[]>> getLiveData() {
        if (shotsLiveData == null) {
            shotsLiveData = new MutableLiveData<>();
        }
        return shotsLiveData;
    }

    public void getNextShots(int page) {
        shotRepository.getSelectedShots(page, shotsLiveData);
    }


}
