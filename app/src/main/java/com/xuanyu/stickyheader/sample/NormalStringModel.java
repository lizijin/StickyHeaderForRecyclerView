package com.xuanyu.stickyheader.sample;

public class NormalStringModel implements TextModel {
    public String text;
    public NormalStringModel(String text){
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
