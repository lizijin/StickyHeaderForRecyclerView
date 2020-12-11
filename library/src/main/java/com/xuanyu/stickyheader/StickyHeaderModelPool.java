/*
 *
 *  @date 创建时间 10/4/20 6:02 PM
 *  @author jiangbin
 *  @email 549974268@qq.com
 *  @description
 *
 */

package com.xuanyu.stickyheader;

import java.util.HashMap;

public class StickyHeaderModelPool {
    private final static HashMap<Class<IStickyHeaderModel<?>>, Pools.SynchronizedPool<IStickyHeaderModel<?>>> sPools = new HashMap<>();

    public static IStickyHeaderModel<?> obtain(Class<IStickyHeaderModel<?>> clazz) {
        if (sPools.get(clazz) == null) {
            sPools.put(clazz, new Pools.SynchronizedPool<IStickyHeaderModel<?>>(20));
        }
        IStickyHeaderModel<?> stickyHeaderModel = sPools.get(clazz).acquire();
        if (stickyHeaderModel != null) {
            System.out.println("StickyHeaderModelPool -- 从缓存中获取");
            return stickyHeaderModel;
        }
        try {
            System.out.println("StickyHeaderModelPool -- 反射获取");

            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void recycle( IStickyHeaderModel<?> stickyHeaderModel) {
        Class clazz = stickyHeaderModel.getClass();
        System.out.println("StickyHeaderModelPool -- recycle "+clazz);

        if (sPools.get(clazz) == null) {
            return;
        }
        sPools.get(clazz).release(stickyHeaderModel);
    }
}
