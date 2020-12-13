package com.xuanyu.stickyheader;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseStickyHeaderModel<T>  {
    // 当前吸顶Model对应RecyclerView上的Model
    private T mRecyclerViewItemModel;

    // 当前吸顶Model对应RecyclerView上的View
    private IStickyHeaderView<T> mRecyclerViewItemView;

    public T getRecyclerViewItemModel() {
        return mRecyclerViewItemModel;
    }

    public void setRecyclerViewItemModel(T data) {
        this.mRecyclerViewItemModel = data;
    }

    public void setRecyclerViewItemView(IStickyHeaderView<T> recyclerViewItemView) {
        this.mRecyclerViewItemView = recyclerViewItemView;
    }

    public IStickyHeaderView<T> getRecyclerViewItem() {
        return this.mRecyclerViewItemView;
    }

    IStickyHeaderView<T> createIfAbsent(RecyclerView recyclerView,Context context, Class clazz) {
        IStickyHeaderView<T> iStickyHeaderView = (IStickyHeaderView<T>) StickyHeaderRegistry.getView(recyclerView,clazz);
        if (iStickyHeaderView == null) {
            iStickyHeaderView = getStickyView(context);
            StickyHeaderRegistry.putView(recyclerView,clazz, iStickyHeaderView);
        }
        return iStickyHeaderView;
    }

    public abstract IStickyHeaderView<T> getStickyView(Context context);
}
