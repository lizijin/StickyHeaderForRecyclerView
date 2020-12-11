package com.xuanyu.stickyheader;

import android.content.Context;

public abstract class BaseStickyHeaderModel<T> implements IStickyHeaderModel<T> {
    private T mRecyclerViewItemModel;

    private IStickyHeaderView<T> mRecyclerViewItemView;

    @Override
    public T getRecyclerViewItemModel() {
        return mRecyclerViewItemModel;
    }

    @Override
    public void setRecyclerViewItemModel(T data) {
        this.mRecyclerViewItemModel = data;
    }

    @Override
    public void setRecyclerViewItemView(IStickyHeaderView<T> recyclerViewItemView) {
        this.mRecyclerViewItemView = recyclerViewItemView;
    }

    @Override
    public IStickyHeaderView<T> getRecyclerVIewItem() {
        return this.mRecyclerViewItemView;
    }

    public IStickyHeaderView<T> createIfAbsent(Context context, Class clazz) {
        IStickyHeaderView<T> iStickyHeaderView = (IStickyHeaderView<T>) StickyHeaderRegistry.getView(clazz);
        if (iStickyHeaderView == null) {
            iStickyHeaderView = getStickyView(context);
            StickyHeaderRegistry.putView(clazz, iStickyHeaderView);
        }
        return iStickyHeaderView;
    }

    @Override
    public abstract IStickyHeaderView<T> getStickyView(Context context);
}
