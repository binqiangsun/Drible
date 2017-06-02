package com.sunbinqiang.drible.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sunbinqiang.drible.db.entity.Shot;
import com.sunbinqiang.drible.repository.ShotListRepository;

import java.util.List;

/**
 * Created by sunbinqiang on 01/06/2017.
 */

public class ShotListViewModel extends ViewModel {

    private ShotListRepository shotRepository;

    public ShotListViewModel() {
        this.shotRepository = ShotListRepository.getInstance();
    }

    public LiveData<List<Shot>> getShots(int page) {
        return shotRepository.getSelectedShots(page);
    }

}
