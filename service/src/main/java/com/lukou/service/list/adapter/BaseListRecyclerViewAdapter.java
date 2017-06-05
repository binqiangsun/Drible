package com.lukou.service.list.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukou.service.R;
import com.lukou.service.list.view.LoadingItemView;
import com.lukou.service.list.view.RetryLoadListener;
import com.lukou.service.list.viewholder.BaseViewHolder;
import com.lukou.service.list.viewholder.ErrorViewHolder;
import com.lukou.service.list.viewholder.LoadingViewHolder;
import com.lukou.service.list.viewholder.PageErrorViewHolder;
import com.lukou.service.list.viewholder.PageLoadingViewHolder;

/**
 * Created by sunbinqiang on 03/06/2017.
 * 处理loading， error等viewholder
 */

public abstract class BaseListRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    /**
     * ListRecyclerViewAdapter 状态控制
     */
    private static final int HEADER_TYPE = 0x10000000; //最多0x00111111个 Header DealItem
    private static final int FOOTER_TYPE = 0x01000000; //最多0x00111111个 Footer DealItem
    private static final int HEADER_FOOTER_TYPE_MASK = 0x11000000;
    private static final int ERROR_ITEM_ID = 0x00100000;
    private static final int ERROR_PAGE_ID = 0x00010000;
    private static final int EMPTY_ID = 0x00001000;
    private static final int ITEM_ID = 0x00000010;
    private static final int LOADING_ITEM_ID = 0x00000000;
    private static final int LOADING_PAGE_ID = 0x00000001;

    protected String errorMsg;
    protected boolean isEnd;
    protected int nextPage = 1;
    protected boolean isError;

    public abstract int getListCount();
    abstract void loadNext();

    private boolean isEmpty() {
        return isEnd && getListCount() == 0;
    }

    private boolean isEnd() {
        return isEnd;
    }

    private boolean isError() {
        return isError;
    }

    private void retry() {
        isError = false;
        notifyDataSetChanged();
    }

    @Override
    public final int getItemCount() {
        int count = 0;
        if (hasHeaderViewHolder()) {
            count += getHeaderViewCount();
        }
        if (hasFooterViewHolder()) {
            count += getFooterViewCount();
        }
        if (isEmpty()) {
            return ++count;
        }
        if (isError() || (!isEnd())) {
            count++;
        }
        return getListCount() + count;
    }

    @Override
    public final int getItemViewType(int position) {
        if (hasHeaderViewHolder() && isPositionInHeaderRange(position)) {
            return getViewTypeForHeader(position);
        }

        if (hasFooterViewHolder() && isPositionInFooterRange(position)) {
            return getViewTypeForFooter(position);
        }

        if (hasHeaderViewHolder()) {
            position -= getHeaderViewCount();
        }

        if (position < getListCount()) {
            return ITEM_ID + getViewTypeForItem(position);
        } else if (isEmpty()) {
            return EMPTY_ID;
        } else if (isError()) {
            return getHeaderViewCount() == 0 && getFooterViewCount() == 0 && getListCount() == 0 ? ERROR_PAGE_ID : ERROR_ITEM_ID;
        } else if (!isEnd()) {
            return getHeaderViewCount() == 0 && getFooterViewCount() == 0 && getListCount() == 0 ? LOADING_PAGE_ID : LOADING_ITEM_ID;
        }

        throw new RuntimeException("unknown item view type for position:" + position);
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;

        if (isHeaderViewType(viewType)) {
            holder = createHeaderViewHolder(parent, getPositionForHeaderViewType(viewType));
            holder.setIsRecyclable(false);
        } else if (isFooterViewType(viewType)) {
            holder = createFooterViewHolder(parent, getPositionForFooterViewType(viewType));
            holder.setIsRecyclable(false);
        } else if (viewType == LOADING_ITEM_ID) {
            holder = new LoadingViewHolder(new LoadingItemView(parent.getContext()));
        } else if (viewType == LOADING_PAGE_ID) {
            BaseViewHolder pageLoadingViewHolder = onCreatePageLoadingViewHolder(parent.getContext(), parent);
            holder = pageLoadingViewHolder != null ? pageLoadingViewHolder : getDefaultPageLoadingViewHolder(parent.getContext(), parent);
        } else if (viewType == ERROR_ITEM_ID) {
            BaseViewHolder itemErrorViewHolder = onCreateItemErrorViewHolder(parent.getContext(), parent);
            holder = itemErrorViewHolder != null ? itemErrorViewHolder : getDefaultItemErrorViewHolder(parent.getContext(), parent);
        } else if (viewType == ERROR_PAGE_ID) {
            BaseViewHolder pageErrorViewHolder = onCreatePageErrorViewHolder(parent.getContext(), parent);
            holder = pageErrorViewHolder != null ? pageErrorViewHolder : getDefaultPageErrorViewHolder(parent.getContext(), parent);
        } else if (viewType == EMPTY_ID) {
            BaseViewHolder emptyViewHolder = onCreateEmptyViewHolder(parent.getContext(), parent);
            holder = emptyViewHolder != null ? emptyViewHolder : getDefaultEmptyViewHolder(parent.getContext(), parent);
        } else {
            holder = onCreateItemViewHolder(parent.getContext(), parent, viewType - ITEM_ID);
        }
        return holder;
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isPositionInHeaderRange(position)) {
            onBindHeaderViewHolder(holder, position);
            return;
        }

        if (isPositionInFooterRange(position)) {
            onBindFooterViewHolder(holder, getOffsetForFootPosition(position));
            return;
        }

        switch (getItemViewType(position)) {
            case LOADING_PAGE_ID:
                onBindPageLoadingViewHolder(holder);
            case LOADING_ITEM_ID: {
                loadNext();
                break;
            }
            case EMPTY_ID: {
                onBindEmptyViewHolder(holder);
                break;
            }
            case ERROR_ITEM_ID: {
                onBindItemErrorViewHolder(holder);
                break;
            }
            case ERROR_PAGE_ID: {
                onBindPageErrorViewHolder(holder);
                break;
            }
            default: {
                position -= getHeaderViewCount();
                onBindItemViewHolder(holder, position);
                break;
            }
        }

    }

    @Override
    public final long getItemId(int position) {
        return position;
    }


    protected abstract BaseViewHolder onCreateItemViewHolder(Context context, ViewGroup parent, int viewType);

    public int getViewTypeForItem(int position) {
        return 0;
    }

    protected abstract void onBindItemViewHolder(BaseViewHolder holder, int position);

    protected int getHeaderViewCount() {
        return 0;
    }

    protected BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent, int position) {
        return null;
    }

    protected void onBindHeaderViewHolder(BaseViewHolder holder, int position) {

    }

    private int getPositionForHeaderViewType(int viewType) {
        return viewType & ~HEADER_FOOTER_TYPE_MASK;
    }

    private boolean isHeaderViewType(int viewType) {
        return hasHeaderViewHolder() && ((viewType & HEADER_FOOTER_TYPE_MASK) == HEADER_TYPE);
    }

    private int getViewTypeForHeader(int position) {
        return HEADER_TYPE | position;
    }

    protected boolean isPositionInHeaderRange(int position) {
        return getHeaderViewCount() > position;
    }

    private boolean hasHeaderViewHolder() {
        return getHeaderViewCount() > 0;
    }

    private BaseViewHolder createHeaderViewHolder(ViewGroup parent, int position) {
        BaseViewHolder viewHolder = onCreateHeaderViewHolder(parent, position);
        if (viewHolder == null) {
            throw new RuntimeException("postion = " + position + ":HeaderViewHolder can't be null");
        }
        return viewHolder;
    }

    protected int getFooterViewCount() {
        return 0;
    }

    private int getOtherViewCount() {
        return getItemCount() - getHeaderViewCount() - getFooterViewCount() - getListCount();
    }

    protected int getOffsetForFootPosition(int position) {
        return position - getHeaderViewCount() - getListCount() - getOtherViewCount();
    }

    private boolean isFooterViewType(int viewType) {
        return hasFooterViewHolder() && ((viewType & HEADER_FOOTER_TYPE_MASK) == FOOTER_TYPE);
    }

    private int getPositionForFooterViewType(int viewType) {
        return viewType & ~HEADER_FOOTER_TYPE_MASK;
    }

    private int getViewTypeForFooter(int position) {
        int positionOffset = getOffsetForFootPosition(position);
        if (positionOffset < 0) {
            throw new RuntimeException("unknown footer view type for position:" + position);
        }
        return FOOTER_TYPE | positionOffset;
    }

    public boolean isPositionInFooterRange(int position) {
        int positionOffset = getOffsetForFootPosition(position);
        if (positionOffset < 0) {
            return false;
        }
        return getFooterViewCount() > positionOffset;
    }

    public boolean isHeaderViewHolder(int position){
        return getHeaderViewCount() > position;
    }

    private boolean hasFooterViewHolder() {
        return getFooterViewCount() > 0;
    }

    private BaseViewHolder createFooterViewHolder(ViewGroup parent, int position) {
        BaseViewHolder viewHolder = onCreateFooterViewHolder(parent, position);
        if (viewHolder == null) {
            throw new RuntimeException("postion = " + position + ":FooterViewHolder can't be null");
        }
        return viewHolder;
    }

    protected BaseViewHolder onCreateFooterViewHolder(ViewGroup parent, int position) {
        return null;
    }

    protected void onBindFooterViewHolder(BaseViewHolder holder, int position) {

    }


    /**
     * 子类可以自由定制EmptyViewHolder的展现
     */
    protected BaseViewHolder onCreateEmptyViewHolder(Context context, ViewGroup parent) {
        return null;
    }

    protected void onBindEmptyViewHolder(BaseViewHolder emptyViewHolder) {

    }

    /**
     * @param context
     * @param parent
     * @return
     */
    private BaseViewHolder getDefaultEmptyViewHolder(Context context, ViewGroup parent) {
        return new DefaultEmptyViewHolder(context, parent);
    }

    protected BaseViewHolder onCreatePageLoadingViewHolder(Context context, ViewGroup parent) {
        return null;
    }

    protected void onBindPageLoadingViewHolder(BaseViewHolder viewHolder) {

    }

    private BaseViewHolder getDefaultPageLoadingViewHolder(Context context, ViewGroup parent) {
        return new PageLoadingViewHolder(context, parent);
    }

    protected BaseViewHolder onCreateItemErrorViewHolder(Context context, ViewGroup parent) {
        return null;
    }

    protected void onBindItemErrorViewHolder(BaseViewHolder viewHolder) {
        if (viewHolder instanceof ErrorViewHolder) {
            ((ErrorViewHolder) viewHolder).setRetry(errorMsg, new RetryLoadListener() {
                @Override
                public void retryLoad() {
                    retry();
                }
            });
        }
    }

    private BaseViewHolder getDefaultItemErrorViewHolder(Context context, ViewGroup parent) {
        return ErrorViewHolder.create(context);
    }

    protected BaseViewHolder onCreatePageErrorViewHolder(Context context, ViewGroup parent) {
        return null;
    }

    protected void onBindPageErrorViewHolder(BaseViewHolder viewHolder) {
        if (viewHolder instanceof PageErrorViewHolder) {
            ((PageErrorViewHolder) viewHolder).setRetryText(errorMsg);
            ((PageErrorViewHolder) viewHolder).setRetry(new RetryLoadListener() {
                @Override
                public void retryLoad() {
                    retry();
                }
            });
        }
    }

    private BaseViewHolder getDefaultPageErrorViewHolder(Context context, ViewGroup parent) {
        return new PageErrorViewHolder(context, parent);
    }

    public static class DefaultEmptyViewHolder extends BaseViewHolder {

        public DefaultEmptyViewHolder(Context context, ViewGroup parent) {
            this(LayoutInflater.from(context).inflate(R.layout.empty_item, parent, false));
        }

        public DefaultEmptyViewHolder(View itemView) {
            super(itemView);
        }

    }

    public static GridLayoutManager.SpanSizeLookup getSpanSizeLookup(final BaseListRecyclerViewAdapter adapter){
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                try {
                    int viewType = adapter.getItemViewType(position);
                    if (!(adapter.isHeaderViewHolder(position)
                            || viewType == ERROR_ITEM_ID
                            || viewType == ERROR_PAGE_ID
                            || viewType == LOADING_ITEM_ID
                            || viewType == LOADING_PAGE_ID
                            || viewType == EMPTY_ID)) {
                        return 1;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                return 2;
            }
        };
    }

}
