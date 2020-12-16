package com.xuanyu.stickyheader.sample.bean;

public interface Naming {
    public static final int PERSON_NAME = 0;//小说主人公类型
    public static final int CUSTOM_BOOK_NAME = 1;//该book对应的recyclerView ItemView实现了IStickyHeaderView接口
    public static final int BOOK_NAME = 2;//小说书名
    public static final int AUTHOR_NAME = 3;//小说作者名字
    String getName();
    int getItemType();
}
