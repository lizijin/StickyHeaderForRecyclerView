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
 *
 */
public interface IStickyHeaderView<T> {

    /**
     * RecyclerView中需要吸顶的View的上限
     * @return
     */
    int getItemViewTop();

    /**
     * 孵化出一个纯粹的接口对象。因为该接口是会被RecyclerView上的ItemView实现。需要将View 和数据剥离开
     * @return
     */
    IStickyHeaderView incubate();

    void setData(T t);


}
