package com.xuanyu.stickyheader;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseStickyHeaderModel<T> {
    // 当前吸顶Model对应RecyclerView上的Model
    private T mRecyclerViewItemModel;

    // 当前吸顶Model对应RecyclerView上的View
    private View mRecyclerViewItemView;


    private View mStickyView;

    public T getRecyclerViewItemModel() {
        return mRecyclerViewItemModel;
    }

      void setRecyclerViewItemModel(T data) {
        this.mRecyclerViewItemModel = data;
    }

    public void setRecyclerViewItemView(View recyclerViewItemView) {
        this.mRecyclerViewItemView = recyclerViewItemView;
    }

    public View getRecyclerViewItemView() {
        return this.mRecyclerViewItemView;
    }

    View createIfAbsent(RecyclerView recyclerView, Context context) {
        Class<?> clazz = mRecyclerViewItemModel.getClass();
        View iStickyHeaderView = StickyHeaderRegistry.getView(recyclerView, clazz);
        if (iStickyHeaderView == null) {
            iStickyHeaderView = getStickyView(context);
            StickyHeaderRegistry.putView(recyclerView, clazz, iStickyHeaderView);
        }
        mStickyView = iStickyHeaderView;
        return iStickyHeaderView;
    }

    public abstract View getStickyView(Context context);

    public abstract void onBindView(View stickyView, T data);


     void onBindView() {
        if (mStickyView instanceof IStickyHeaderView) {
            ((IStickyHeaderView) mStickyView).setData(getRecyclerViewItemModel());
        } else {
            onBindView(mStickyView, getRecyclerViewItemModel());
        }
    }
}
