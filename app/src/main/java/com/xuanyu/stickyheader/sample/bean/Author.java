package com.xuanyu.stickyheader.sample.bean;

public class Author implements Naming {
    public String name;

    public Author(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getItemType() {
        return Naming.AUTHOR_NAME;
    }
}
