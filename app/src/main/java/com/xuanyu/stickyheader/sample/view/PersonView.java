package com.xuanyu.stickyheader.sample.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanyu.stickyheader.sample.R;

public class PersonView extends FrameLayout {
    public final TextView mTextView;

    public PersonView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_person, this);
        mTextView = findViewById(R.id.normal_text);
    }
}
