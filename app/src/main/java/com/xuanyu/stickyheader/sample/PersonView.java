package com.xuanyu.stickyheader.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class PersonView extends FrameLayout {
    public final TextView mTextView;

    public PersonView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_normal,this);
        mTextView = findViewById(R.id.normal_text);
    }
}
