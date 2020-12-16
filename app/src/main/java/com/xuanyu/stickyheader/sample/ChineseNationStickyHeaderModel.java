package com.xuanyu.stickyheader.sample;

import android.content.Context;
import android.view.View;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.sample.bean.AmericanNation;

public class ChineseNationStickyHeaderModel extends BaseStickyHeaderModel<AmericanNation> {

    @Override
    public View getStickyView(Context context) {
        System.out.println("jiangbin getView");
        AmericanNationView headerView = new AmericanNationView(context);
        return headerView;
    }

    @Override
    public void onBindView(View view, AmericanNation data) {

    }
}
