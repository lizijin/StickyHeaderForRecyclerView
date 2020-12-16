package com.xuanyu.stickyheader.sample.bean;

public class RouteItem {
    public String mainTitle;
    public String subTitle;
    public Class activityClazz;

    public RouteItem(String mainTitle, String subTitle, Class activityClazz) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.activityClazz = activityClazz;
    }
}
