package com.xuanyu.stickyheader.sample;

public class HeaderStringModel implements TextModel {
    public String headerText;

    public HeaderStringModel(String headerText) {
        this.headerText = headerText;
    }

    @Override
    public String getText() {
        return headerText;
    }
}
