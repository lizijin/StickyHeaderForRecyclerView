package com.xuanyu.stickyheader.sample.bean;

/**
 * 书名
 */
public class Book implements Naming {
    public String name;

    public Book(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
