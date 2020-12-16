package com.xuanyu.stickyheader.sample.bean;

public class AmericanNation implements Naming {
    public String name;

    public AmericanNation(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
