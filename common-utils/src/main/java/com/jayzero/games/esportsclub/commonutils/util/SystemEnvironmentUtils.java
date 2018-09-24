package com.jayzero.games.esportsclub.commonutils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SystemEnvironmentUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version SystemEnvironmentUtils.java, v0.1
 * @date 2018/04/20
 */
public class SystemEnvironmentUtils {

    private static final Logger logger = LoggerFactory.getLogger(SystemEnvironmentUtils.class);

    /**
     * systemEnvKey 系统变量名称
     *
     * @return 系统变量名称对应的系统变量的值, 如果不存在或者有异常, 都返回null
     */
    public static String fetchSystemEnvWithoutException(String systemEnvKey) {
        try {
            return System.getenv(systemEnvKey);
        } catch (Exception e) {
            logger.error("error occurred when fetchSystemEnvWithoutException.", e);
            return null;
        }
    }
}
