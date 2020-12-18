package com.xuanyu.stickyheader.sample.bean;

/**
 * 书名
 */
public class Book implements Naming {
    public String name;
    public boolean small;

    public Book(String name,boolean small) {
        this.name = name;
        this.small = small;
    }
    public Book(String name) {
        this(name,false);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getItemType() {
        if(!small)
        return Naming.BOOK_NAME;
        return SMALL_BOOK_NAME;
    }
}
