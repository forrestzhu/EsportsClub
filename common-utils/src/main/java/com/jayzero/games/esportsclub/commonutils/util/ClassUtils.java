package com.jayzero.games.esportsclub.commonutils.util;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version ClassUtils.java, v0.1
 * @date 2018/01/30
 */
public class ClassUtils {

    /**
     * 根据className加载class, 如果不存在则返回null
     *
     * @param className 类名
     * @return Class
     */
    public static Class<?> loadClass(String className) {
        Class<?> clazz = null;

        if (StringUtils.isBlank(className)) {
            return null;
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            // skip
        }

        ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
        if (ctxClassLoader != null) {
            try {
                clazz = ctxClassLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                // skip
            }
        }

        return clazz;
    }
}
