package com.jayzero.games.esportsclub.commonutils.util;

import org.apache.commons.lang3.StringUtils;

/**
 * ApplicationEnvChecker
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version ApplicationEnvChecker.java, v0.1
 * @date 2018/05/26
 */
public class ApplicationEnvChecker {

    /**
     * 本地环境
     */
    private static final String ENV_LOCAL = "local";

    /**
     * 日常环境
     */
    private static final String ENV_DAILY = "dev";

    /**
     * 预发环境
     */
    private static final String ENV_PRE = "pre";

    /**
     * 生产环境
     */
    private static final String ENV_PRODUCTION = "prod";

    /**
     * 私有云环境
     */
    private static final String ENV_PRIVATE_CLOUD = "private-cloud";

    /**
     * 公有云环境
     */
    private static final String ENV_PUBLIC_CLOUD = "public-cloud";

    /**
     * 判断是否不是生产环境
     *
     * @param env env
     * @return true if not product env, false otherwise
     */
    public static boolean checkIfNotProductEnv(String env) {
        return StringUtils.isNotBlank(env)
            && !StringUtils.contains(env.toLowerCase(), ENV_PRODUCTION);
    }

    /**
     * 判断当前环境是否是日常或者local
     *
     * @param env 环境
     * @return true if dev/local, false otherwise
     */
    public static boolean isDevOrLocalEnv(String env) {
        if (StringUtils.isNotBlank(env)) {
            return StringUtils.equals(env.toLowerCase(), ENV_DAILY) || StringUtils.equals(env.toLowerCase(), ENV_LOCAL);
        } else {
            return false;
        }
    }

    /**
     * 判断当前环境是否是预发环境
     *
     * @param env 环境
     * @return true if dev/local, false otherwise
     */
    public static boolean isPre(String env) {
        if (StringUtils.isNotBlank(env)) {
            return StringUtils.equals(env.toLowerCase(), ENV_PRE);
        } else {
            return false;
        }
    }
}
