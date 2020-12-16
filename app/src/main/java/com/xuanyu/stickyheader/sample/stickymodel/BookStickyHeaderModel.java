package com.xuanyu.stickyheader.sample.stickymodel;

import android.content.Context;
import android.view.View;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.sample.bean.Book;
import com.xuanyu.stickyheader.sample.view.BookView;

public class BookStickyHeaderModel extends BaseStickyHeaderModel<Book> {

    @Override
    public View getStickyView(Context context) {
        System.out.println("jiangbin getView");
        BookView bookView = new BookView(context);
        return bookView;
    }

    @Override
    public void onBindView(View view, Book data) {

        ((BookView) view).setData(new Book(data.name + "吸顶"));
    }
}
