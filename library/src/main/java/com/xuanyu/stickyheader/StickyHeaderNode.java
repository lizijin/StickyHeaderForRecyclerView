/*
 *
 *  @date 创建时间 9/30/20 5:23 PM
 *  @author jiangbin
 *  @email 549974268@qq.com
 *  @description
 *
 */

package com.xuanyu.stickyheader;

/**
 * 吸顶的Item链表。用来记录上一个吸顶的Item
 * 列表向上滑动的时候，建立链表
 * 列表向下滑动的时候，通过查询上一个吸顶的Item 替换掉当前的吸顶
 */
public class StickyHeaderNode<T> {

    private StickyHeaderNode<T> mPrevNode;//记录上一个吸顶的Node

    private IStickyHeaderModel<T> mModel;

    public void setPrevNode(StickyHeaderNode<T> prevNode) {
        this.mPrevNode = prevNode;
    }

    public IStickyHeaderModel<T> getStickyHeaderModel() {
        return mModel;
    }

    public void setStickyHeaderModel(IStickyHeaderModel<T> model) {
        this.mModel = model;
    }

    public StickyHeaderNode<T> getPrevNode() {
        return mPrevNode;
    }
}
