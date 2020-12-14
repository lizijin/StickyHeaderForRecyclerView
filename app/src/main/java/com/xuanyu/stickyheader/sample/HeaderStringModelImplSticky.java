package com.xuanyu.stickyheader.sample;

public class HeaderStringModelImplSticky implements TextModel {
    public String headerText;

    public HeaderStringModelImplSticky(String headerText) {
        this.headerText = headerText;
    }

    @Override
    public String getText() {
        return headerText;
    }
}
