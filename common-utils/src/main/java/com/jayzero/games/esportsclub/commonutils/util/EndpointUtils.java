package com.jayzero.games.esportsclub.commonutils.util;

import org.apache.commons.lang3.StringUtils;

/**
 * EndpointUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version EndpointUtils.java, v0.1
 * @date 2018/01/30
 */
public class EndpointUtils {

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final String PROTOCOL = "://";

    /**
     * 将endpoint转换为http://开头的字符串
     *
     * @param endpoint endpoint
     * @return converted endpoint
     */
    public static String convert2HttpEndpoint(String endpoint) {
        endpoint = StringUtils.trim(endpoint);
        ParamValidator.validateParamNotBlank(endpoint, "endpoint");
        if (!endpoint.contains(PROTOCOL)) {
            endpoint = HTTP + endpoint;
        }

        if (endpoint.endsWith(StringConstants.SLASH)) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }
        return endpoint;
    }
}
