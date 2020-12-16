package com.xuanyu.stickyheader.sample.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanyu.stickyheader.IStickyHeaderView;
import com.xuanyu.stickyheader.sample.R;
import com.xuanyu.stickyheader.sample.bean.CustomBook;

public class CustomBookView extends FrameLayout implements IStickyHeaderView<CustomBook> {
    private TextView mTextView;

    public CustomBookView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_book, this);
        mTextView = findViewById(R.id.header_text);
    }


    @Override
    public int getItemViewTop() {
        return getTop() - 200;
    }


    @Override
    public void onBindView(CustomBook customBook) {
        mTextView.setText(customBook.getName() + "吸顶");
    }

    public void setData(CustomBook customBook) {
        mTextView.setText(customBook.getName());
    }
}
