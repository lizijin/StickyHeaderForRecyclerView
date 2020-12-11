/*
 *
 *  @date 创建时间 10/1/20 10:55 AM
 *  @author jiangbin
 *  @email 549974268@qq.com
 *  @description
 *
 */

package com.xuanyu.stickyheader;

public interface StickyHeaderAdapter<T> {
    T getItem(int position);

    IStickyHeaderModel<T> transferToStickyHeaderModel(int position);
}
