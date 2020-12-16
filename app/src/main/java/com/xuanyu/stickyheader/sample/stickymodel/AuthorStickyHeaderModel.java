package com.xuanyu.stickyheader.sample.stickymodel;

import android.content.Context;
import android.view.View;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.sample.bean.Author;
import com.xuanyu.stickyheader.sample.bean.Book;
import com.xuanyu.stickyheader.sample.view.AuthorView;
import com.xuanyu.stickyheader.sample.view.BookView;

public class AuthorStickyHeaderModel extends BaseStickyHeaderModel<Author> {

    @Override
    public View getStickyView(Context context) {
        System.out.println("jiangbin getView");
        AuthorView authorView = new AuthorView(context);
        return authorView;
    }

    @Override
    public void onBindView(View view, Author data) {

        ((AuthorView) view).setData(new Author(data.name + "吸顶"));
    }
}
