package com.htwokey.htwokeysecurity.utils;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author hchbo
 * @date 2023/4/15 20:02
 */
public class BeanCopyUtil {

    /**
     * Bean属性复制工具方法。
     *
     * @param dest 目标对象
     * @param src 源对象
     */
    public static void copyBeanProp(Object dest, Object src) {
        try {
            BeanUtil.copyProperties(src, dest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
