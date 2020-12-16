/*
 *
 *  @date 创建时间 9/30/20 10:36 PM
 *  @author jiangbin
 *  @email 549974268@qq.com
 *  @description
 *
 */

package com.xuanyu.stickyheader;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.WeakHashMap;

public class StickyHeaderRegistry {
    private static WeakHashMap<RecyclerView, HashSet<Class>> sRegistryForHeadSticky = new WeakHashMap();

    public static void register(RecyclerView recyclerView, Class clazz) {
        HashSet<Class> hashSet = sRegistryForHeadSticky.get(recyclerView);
        if (hashSet == null) {
            hashSet = new HashSet<>();
        }
        hashSet.add(clazz);
    }

    /**
     * recyclerView上的Item的数据类型是否支持吸顶效果
     *
     * @param recyclerView
     * @param clazz
     * @return
     */
    public boolean isStickyHeaderAbility(RecyclerView recyclerView, Class clazz) {
        return sRegistryForHeadSticky.get(recyclerView) != null && sRegistryForHeadSticky.get(recyclerView).contains(clazz);
    }

    private static HashMap<Class, Class> sTransferMap = new HashMap<>();
    private static HashSet<Class> sExcludeTypeMap = new HashSet<>();

    //todo 针对不同的RecyclerView需要区别对待
    public static <T> void registerTransfer(Class<T> fromClazz, Class<? extends BaseStickyHeaderModel<T>> toClazz) {
        sTransferMap.put(fromClazz, toClazz);
    }

    public static <T> Class<? extends BaseStickyHeaderModel<T>> getClazz(Class<T> fromClazz) {
        return sTransferMap.get(fromClazz);
    }

    public static <T> void registerExclude(Class<T> clazz) {
        sExcludeTypeMap.add(clazz);
    }

    public static boolean isExclude(Class clazz) {
        return sExcludeTypeMap.contains(clazz);
    }

    private static WeakHashMap<RecyclerView, HashMap<Class, View>> sViews = new WeakHashMap<>();

    static void putView(RecyclerView recyclerView, Class clazz, View view) {
        HashMap<Class, View> hashMap = sViews.get(recyclerView);
        if (hashMap == null) {
            hashMap = new HashMap<>();
            sViews.put(recyclerView, hashMap);
        }
        hashMap.put(clazz, view);
    }

    static View getView(RecyclerView recyclerView, Class clazz) {
        if (sViews.get(recyclerView) == null) return null;
        return sViews.get(recyclerView).get(clazz);
    }

}
