package com.lukou.service.list.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.lukou.service.http.Resource;

import java.util.ArrayList;
import java.util.Arrays;

import rx.Observable;
import rx.functions.Action1;

/**
 * @author Sunbinqiang
 * 处理分页数据
 * T : 列表的数据类型
 */
public abstract class ListRecyclerViewAdapter<T> extends BaseListRecyclerViewAdapter {

    private final ArrayList<T> list = new ArrayList<>();
    private String errorMsg;
    private LifecycleOwner lifecycleOwner;

    /**
     * 适配liveData的构造函数
     * @param lifecycleOwner
     * @param liveData
     */
    public ListRecyclerViewAdapter(LifecycleOwner lifecycleOwner, MutableLiveData<Resource<T[]>> liveData){
        this.lifecycleOwner = lifecycleOwner;
        liveData.observe(lifecycleOwner, new Observer<Resource<T[]>>() {
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

    /**
     * 默认适配RxJava的构造函数
     */
    public ListRecyclerViewAdapter() {

    }

    /**
     * 加载下一页
     */
    @Override
    void loadNext() {
        Observable<T[]> observable = request(nextPage);
        if (observable == null) {
            //非RxJava请求方式
            return ;
        }
        observable.subscribe(new Action1<T[]>() {
            @Override
            public void call(T[] ts) {
                requestSuccess(ts);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                requestFailed(throwable.getMessage());
            }
        });
    }

    @Override
    public int getListCount() {
        return list.size();
    }

    /**
     * 列表api请求
     * @param nextId
     * @return
     */
    protected abstract Observable<T[]> request(int nextId);

    /**
     * 请求成功
     * @param list
     */
    private void requestSuccess(T[] list){
        setResultList(list);
    }

    /**
     * 请求失败
     * @param errorMsg
     */
    private void requestFailed(String errorMsg) {
        this.errorMsg = errorMsg;
        isError = true;
    }

    /**
     * 添加列表数据
     * @param list
     * @param newCommingArray
     */
    private void sortList(ArrayList<T> list, T[] newCommingArray) {
        if (newCommingArray != null) {
            list.addAll(Arrays.asList(newCommingArray));
        }
    }

    /**
     * 设置数据源
     * @param resultList
     */
    private void setResultList(T[] resultList) {
        if (resultList != null) {
            if (nextPage == 0) {
                //刷新， 对比原来的数据
                if(!Arrays.equals(list.toArray(), resultList)){
                    list.clear();
                    sortList(list, resultList);
                    notifyItemRangeChanged(0, getItemCount());
                }
            } else if (resultList.length == 0) {
                //当前页数据为空的时候， 不再请求下一页。
                isEnd = true;
                notifyItemChanged(getItemCount());
                return;
            } else {
                //分页加载，直接加在后面即可
                int insertPosition = getItemCount();
                sortList(list, resultList);
                notifyItemInserted(insertPosition);
            }
            nextPage ++;
        }
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

    public ArrayList<T> getList() {
        return list;
    }
}
