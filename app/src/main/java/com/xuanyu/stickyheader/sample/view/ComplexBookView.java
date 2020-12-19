package com.xuanyu.stickyheader.sample.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;
import com.xuanyu.stickyheader.sample.R;
import com.xuanyu.stickyheader.sample.bean.Book;

public class ComplexBookView extends FrameLayout {
    private TextView mTitleTextView;
    private TextView mDescTextView;
    private ImageView mImageView;

    public ComplexBookView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_complex_book, this);
        mTitleTextView = findViewById(R.id.title_text);
        mDescTextView = findViewById(R.id.desc_text);
        mImageView = findViewById(R.id.image_book);
    }


    public void setData(final Book book) {
        mTitleTextView.setText(book.getName());
        mDescTextView.setText("金庸编写的" + book.name);
        Picasso.get().load(book.image).into(mImageView);

        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了" + book.name, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
