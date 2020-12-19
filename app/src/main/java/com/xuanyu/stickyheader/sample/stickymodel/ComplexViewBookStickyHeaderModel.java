package com.xuanyu.stickyheader.sample.stickymodel;

import android.content.Context;
import android.view.View;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.sample.bean.Book;
import com.xuanyu.stickyheader.sample.view.BookView;
import com.xuanyu.stickyheader.sample.view.ComplexBookView;

public class ComplexViewBookStickyHeaderModel extends BaseStickyHeaderModel<Book> {

    @Override
    public View getStickyView(Context context) {
        System.out.println("jiangbin getView");
        ComplexBookView bookView = new ComplexBookView(context);
        return bookView;
    }

    @Override
    public void onBindView(View view, Book data) {

        ((ComplexBookView) view).setData(data);
    }
}
