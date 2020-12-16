package com.xuanyu.stickyheader.sample.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanyu.stickyheader.sample.R;
import com.xuanyu.stickyheader.sample.bean.Author;
import com.xuanyu.stickyheader.sample.bean.Book;

public class AuthorView extends FrameLayout {
    public final TextView mTextView;

    public AuthorView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_author, this);
        mTextView = findViewById(R.id.author_text);
    }
    public void setData(Author author) {
        mTextView.setText(author.getName());
    }
}
