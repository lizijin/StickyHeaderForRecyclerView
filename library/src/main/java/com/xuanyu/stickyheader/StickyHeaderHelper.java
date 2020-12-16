/*
 *
 *  @date 创建时间 10/1/20 7:55 AM
 *  @author jiangbin
 *  @email 549974268@qq.com
 *  @description
 *
 */

package com.xuanyu.stickyheader;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.WeakHashMap;

public class StickyHeaderHelper {
    private static WeakHashMap<RecyclerView, StickyHeaderOnScrollListener<?>> recordMap = new WeakHashMap<>();

    public static <T> void init(RecyclerView recyclerView, int headerViewTop, ViewGroup stickyHeaderLayout) {
        if (recordMap.get(recyclerView) == null) {
            StickyHeaderOnScrollListener<T> listener = new StickyHeaderOnScrollListener<T>(recyclerView, stickyHeaderLayout, headerViewTop);
            recordMap.put(recyclerView, listener);
            recyclerView.addOnScrollListener(listener);
        }
    }

    public static <T> void init(final RecyclerView recyclerView, final ViewGroup stickyHeaderLayout, final int offset) {
        if (recordMap.get(recyclerView) == null) {
            stickyHeaderLayout.post(new Runnable() {
                @Override
                public void run() {
                    int headerViewTop = stickyHeaderLayout.getTop();
                    StickyHeaderOnScrollListener<T> listener = new StickyHeaderOnScrollListener<T>(recyclerView, stickyHeaderLayout, headerViewTop);
                    listener.setOffset(offset);
                    recordMap.put(recyclerView, listener);
                    recyclerView.addOnScrollListener(listener);
                }
            });

        }
    }

    public static void turnStickyHeader(boolean on, RecyclerView recyclerView) {
        StickyHeaderOnScrollListener<?> listener = recordMap.get(recyclerView);
        if (listener == null) return;
        listener.turnStickyHeader(on);
    }

    /**
     * @param stickyHeaderAdapter
     * @param position
     * @param <T>
     * @return
     */
    public static <T> BaseStickyHeaderModel<T> transferToStickyHeaderModel(StickyHeaderAdapter<T> stickyHeaderAdapter, int position) {
        T item = stickyHeaderAdapter.getItem(position);
        System.out.println("RecyclerViewAdapter postion " + position + " item " + item.getClass());
        if (StickyHeaderRegistry.isExclude(item.getClass())) {
//            return new ExcludeStickyHeaderModel<T>();
        }
        Class clazz = StickyHeaderRegistry.getClazz(item.getClass());
        if (clazz == null) return null;
        System.out.println("RecyclerViewAdapter clazz " + clazz.getSimpleName());
//            BaseStickyHeaderModel<T> iStickyHeaderModel = ((BaseStickyHeaderModel<T>) clazz.newInstance());
        BaseStickyHeaderModel<T> iStickyHeaderModel = StickyHeaderModelPool.obtain(clazz);
        iStickyHeaderModel.setRecyclerViewItemModel(item);
        return iStickyHeaderModel;
    }
}
