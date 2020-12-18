package com.xuanyu.stickyheader.sample.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanyu.stickyheader.sample.R;
import com.xuanyu.stickyheader.sample.bean.Book;

public class SmallBookView extends FrameLayout {
    private TextView mTextView;

    public SmallBookView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_small_book, this);
        mTextView = findViewById(R.id.header_text);
    }


    public void setData(Book book) {
        mTextView.setText(book.getName());
    }
}
