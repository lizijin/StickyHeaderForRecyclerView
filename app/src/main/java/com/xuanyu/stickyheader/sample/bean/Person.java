package com.xuanyu.stickyheader.sample.bean;

public class Person implements Naming {
    public String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getItemType() {
        return Naming.PERSON_NAME;

    }
}
