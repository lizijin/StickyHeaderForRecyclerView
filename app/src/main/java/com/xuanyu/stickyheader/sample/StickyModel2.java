package com.xuanyu.stickyheader.sample;

import android.content.Context;
import android.view.View;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;

public class StickyModel2 extends BaseStickyHeaderModel<HeaderStringModel> {

    @Override
    public View getStickyView(Context context) {
        System.out.println("jiangbin getView");
        HeaderView headerView = new HeaderView(context);
        return headerView;
    }

    @Override
    public void onBindView(View view, HeaderStringModel data) {
        ((HeaderView) view).setData(data);
    }
}
