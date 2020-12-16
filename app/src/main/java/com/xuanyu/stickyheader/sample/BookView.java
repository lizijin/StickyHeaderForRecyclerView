package com.xuanyu.stickyheader.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanyu.stickyheader.sample.bean.Book;

public class BookView extends FrameLayout {
    private TextView mTextView;

    public BookView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_header_no_sticky, this);
        mTextView = findViewById(R.id.header_text);
    }


    public void setData(Book headerStringModel) {
        mTextView.setText(headerStringModel.getName());
    }
}
