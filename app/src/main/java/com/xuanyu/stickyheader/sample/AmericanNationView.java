package com.xuanyu.stickyheader.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanyu.stickyheader.IStickyHeaderView;
import com.xuanyu.stickyheader.sample.bean.AmericanNation;

public class AmericanNationView extends FrameLayout implements IStickyHeaderView<AmericanNation> {
   private TextView mTextView;
    public AmericanNationView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_header,this);
        mTextView = findViewById(R.id.header_text);
    }



    @Override
    public int getItemViewTop() {
        return getTop();
    }


    @Override
    public void setData(AmericanNation headerStringModel) {
        mTextView.setText(headerStringModel.getName()+"吸顶");
    }
    public void setData2(AmericanNation headerStringModel) {
        mTextView.setText(headerStringModel.getName());
    }
}
