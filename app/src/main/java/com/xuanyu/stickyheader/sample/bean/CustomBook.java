package com.xuanyu.stickyheader.sample.bean;

/**
 * 书名
 */
public class CustomBook implements Naming {
    public String name;

    public CustomBook(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getItemType() {
        return Naming.CUSTOM_BOOK_NAME;
    }
}
