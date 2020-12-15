package com.xuanyu.stickyheader.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanyu.stickyheader.IStickyHeaderView;

public class HeaderViewImplSticky extends FrameLayout implements IStickyHeaderView<HeaderStringModelImplSticky> {
   private TextView mTextView;
    public HeaderViewImplSticky(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_header,this);
        mTextView = findViewById(R.id.header_text);
    }



    @Override
    public int getItemViewTop() {
        return getTop();
    }


    @Override
    public void setData(HeaderStringModelImplSticky headerStringModel) {
        mTextView.setText(headerStringModel.getText()+"吸顶");
    }
    public void setData2(HeaderStringModelImplSticky headerStringModel) {
        mTextView.setText(headerStringModel.getText());
    }
}
