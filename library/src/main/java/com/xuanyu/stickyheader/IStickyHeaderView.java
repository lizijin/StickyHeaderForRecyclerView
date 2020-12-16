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
     *
     * @return
     */
    int getItemViewTop();


    void onBindView(T t);


}
