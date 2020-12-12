package com.xuanyu.stickyheader.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanyu.stickyheader.IStickyHeaderView;

public class HeaderView extends FrameLayout implements IStickyHeaderView<HeaderStringModel> {
   private TextView mTextView;
    public HeaderView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_header,this);
        mTextView = findViewById(R.id.header_text);
    }



    @Override
    public int getItemViewTop() {
        return getTop();
    }


    @Override
    public void setData(HeaderStringModel headerStringModel) {
        mTextView.setText(headerStringModel.getText());
    }
}
