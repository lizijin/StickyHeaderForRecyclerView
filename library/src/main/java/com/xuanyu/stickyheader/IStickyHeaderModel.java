///*
// *
// *  @date 创建时间 9/30/20 5:23 PM
// *  @author jiangbin
// *  @email 549974268@qq.com
// *  @description
// *
// */
//
//package com.xuanyu.stickyheader;
//
//import android.content.Context;
//
// interface BaseStickyHeaderModel<T> {
//
//    /**
//     * 当前吸顶Model对应RecyclerView上的Model
//     *
//     * @return
//     */
//    T getRecyclerViewItemModel();
//
//    void setRecyclerViewItemModel(T data);
//
//    /**
//     * 当前吸顶Model对应RecyclerView上的View
//     *
//     * @param stickyHeaderView
//     */
//    void setRecyclerViewItemView(IStickyHeaderView<T> stickyHeaderView);
//
//    IStickyHeaderView<T> getRecyclerVIewItem();
//
//    /**
//     * 获取真正的吸顶View
//     * @param context
//     * @return
//     */
//    IStickyHeaderView<T> getStickyView(Context context);
//
////    IStickyHeaderView<T> createIfAbsent(Context context, Class clazz) ;
//
//}
