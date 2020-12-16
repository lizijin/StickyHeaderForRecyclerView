package com.xuanyu.stickyheader.sample.stickymodel;

import android.content.Context;
import android.view.View;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.sample.bean.CustomBook;
import com.xuanyu.stickyheader.sample.view.CustomBookView;

public class CustomBookStickyHeaderModel extends BaseStickyHeaderModel<CustomBook> {

    @Override
    public View getStickyView(Context context) {
        System.out.println("jiangbin getView");
        CustomBookView customBookView = new CustomBookView(context);
        return customBookView;
    }

    @Override
    public void onBindView(View view, CustomBook data) {
        //CustomBookView实现了 IStickyHeaderView 不需要实现该方法
    }
}
