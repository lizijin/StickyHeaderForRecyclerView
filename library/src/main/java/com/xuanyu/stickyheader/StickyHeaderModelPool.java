/*
 *
 *  @date 创建时间 10/4/20 6:02 PM
 *  @author jiangbin
 *  @email 549974268@qq.com
 *  @description
 *
 */

package com.xuanyu.stickyheader;

import android.util.Log;

import java.util.HashMap;

public class StickyHeaderModelPool {
    private final static HashMap<Class<BaseStickyHeaderModel<?>>, Pools.SynchronizedPool<BaseStickyHeaderModel<?>>> sPools = new HashMap<>();

    public static BaseStickyHeaderModel<?> obtain(Class<BaseStickyHeaderModel<?>> clazz) {
        if (sPools.get(clazz) == null) {
            sPools.put(clazz, new Pools.SynchronizedPool<BaseStickyHeaderModel<?>>(50));
        }
        BaseStickyHeaderModel<?> stickyHeaderModel = sPools.get(clazz).acquire();
        if (stickyHeaderModel != null) {
            Log.e(StickyHeaderModelPool.class.getSimpleName(), "StickyHeaderModelPool -- 从缓存中获取");
            return stickyHeaderModel;
        }
        try {
            //todo jiangxuanyu 想个办法 如果反射次数过多 开发时直接报错
            Log.e(StickyHeaderModelPool.class.getSimpleName(), "StickyHeaderModelPool -- 反射获取");
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void recycle(BaseStickyHeaderModel<?> stickyHeaderModel) {
        Class clazz = stickyHeaderModel.getClass();
        System.out.println("StickyHeaderModelPool -- recycle " + clazz);

        if (sPools.get(clazz) == null) {
            return;
        }
        sPools.get(clazz).release(stickyHeaderModel);
    }
}
