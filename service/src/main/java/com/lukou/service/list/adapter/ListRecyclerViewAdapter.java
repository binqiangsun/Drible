package com.lukou.service.list.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.lukou.service.http.Resource;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Sunbinqiang
 * 处理分页数据
 */
public abstract class ListRecyclerViewAdapter<T, M> extends BaseListRecyclerViewAdapter {

    private final ArrayList<T> list = new ArrayList<>();
    private String errorMsg;
    private LifecycleOwner lifecycleOwner;
    private MutableLiveData<Resource<T[]>> liveDataList;
    protected M viewModel;

    public ListRecyclerViewAdapter(LifecycleOwner lifecycleOwner, MutableLiveData<Resource<T[]>> liveData, M viewModel){
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = viewModel;
        liveDataList = liveData;
        liveDataList.observe(lifecycleOwner, new Observer<Resource<T[]>>() {
            @Override
            public void onChanged(@Nullable Resource<T[]> result) {
                if (result.status == Resource.SUCCESS) {
                    requestSuccess(result.data);
                } else if (result.status == Resource.ERROR) {
                    requestFailed(result.message);
                }
            }
        });
    }

    @Override
    void loadNext() {
        loadNext(nextPage);
    }

    @Override
    public int getListCount() {
        return list.size();
    }

    private void loadNext(int nextIndex) {
        request(nextIndex);
    }

    /**
     *
     * 列表api请求
     * @param nextId
     * @return
     */
    protected abstract void request(int nextId);

    private void requestSuccess(T[] list){
        setResultList(list);
        onRequestFinished();
    }

    private void requestFailed(String errorMsg) {
        errorMsg = errorMsg;
        isError = true;
        onRequestFinished();
    }

    private void sortList(ArrayList<T> list, T[] newCommingArray) {
        if (newCommingArray != null) {
            list.addAll(Arrays.asList(newCommingArray));
        }
    }

    private void setResultList(T[] resultList) {
        if (resultList != null) {
            if (nextPage == 0) {
                //刷新， 对比原来的数据
                if(!Arrays.equals(list.toArray(), resultList)){
                    list.clear();
                    sortList(list, resultList);
                    notifyItemRangeChanged(0, getItemCount());
                }
            } else {
                //分页加载，直接加在后面即可
                int insertPosition = getItemCount();
                sortList(list, resultList);
                notifyItemInserted(insertPosition);
            }
            isEnd = resultList.length == 0;
            nextPage ++;
        }
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public ArrayList<T> getList() {
        return list;
    }

    @CallSuper
    protected void onRequestFinished() {

    }


    private void setError(String errorMsg) {
        isError = true;
        this.errorMsg = errorMsg;
        notifyDataSetChanged();
    }

    /**
     * 数据重置，刷新列表
     *
     * @param clearPreDataWhenRefreshing 下拉刷新时建议设为false，其它情况一般为true
     */
    public void reset(boolean clearPreDataWhenRefreshing) {
        errorMsg = null;
        nextPage = 0;
        isError = false;
        if (clearPreDataWhenRefreshing) {
            list.clear();
            isEnd = false;
            notifyDataSetChanged();
        } else {
            loadNext();
        }
    }

    public void removeItem(T item) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(item)) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            list.remove(index);
            index += getHeaderViewCount();
            notifyItemRemoved(index);
        }
    }

}
