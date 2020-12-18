package com.xuanyu.stickyheader.sample.stickymodel;

import android.content.Context;
import android.view.View;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.sample.bean.Book;
import com.xuanyu.stickyheader.sample.view.SmallBookView;

public class SmallBookStickyHeaderModel extends BaseStickyHeaderModel<Book> {

    @Override
    public View getStickyView(Context context) {
        System.out.println("jiangbin getView");
        SmallBookView bookView = new SmallBookView(context);
        return bookView;
    }

    @Override
    public void onBindView(View view, Book data) {

        ((SmallBookView) view).setData(new Book(data.name + "吸顶"));
    }
}
