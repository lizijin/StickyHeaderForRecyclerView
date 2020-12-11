package com.xuanyu.stickyheader.sample;

import android.content.Context;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.IStickyHeaderView;

public class StickyModel extends BaseStickyHeaderModel<HeaderStringModel> {

    @Override
    public IStickyHeaderView getStickyView(Context context) {
        System.out.println("jiangbin getView");
        HeaderView headerView = new HeaderView(context);
        headerView.setData(getRecyclerViewItemModel());
        return headerView;
    }
}
