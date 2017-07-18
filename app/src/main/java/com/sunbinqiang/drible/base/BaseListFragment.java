package com.sunbinqiang.drible.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lukou.service.list.adapter.ListRecyclerViewAdapter;
import com.sunbinqiang.drible.R;


/**
 * Created by sunbinqiang on 26/11/2016.
 */

public abstract class BaseListFragment<T> extends BaseFragment implements LifecycleRegistryOwner {

    private ListRecyclerViewAdapter<T> adapter;
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    protected int getLayoutId() {
        return R.layout.swipe_recycle_layout;
    }

    @Override
    protected void setViews(View view) {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary, R.color.colorPrimary,
                R.color.colorAccent, R.color.colorPrimary);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(4, 4, 4,
                        4);
            }
        });
        this.adapter = getAdapter();
        recyclerView.setAdapter(this.adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(adapter != null) {
                    adapter.reset(false);
                }
            }
        });
    }

    protected abstract ListRecyclerViewAdapter<T> getAdapter();

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }
}
