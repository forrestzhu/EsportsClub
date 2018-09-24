package com.jayzero.games.esportsclub.commonutils.util;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.Maps;
import com.jayzero.games.esportsclub.commonutils.exception.EsportsClubException;
import com.jayzero.games.esportsclub.commonutils.exception.errorcode.UtilErrorCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpParamUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version HttpParamUtils.java, v0.1
 * @date 2017/10/01
 */
public class HttpParamUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpParamUtils.class);

    /**
     * 将对象转换为http参数, map
     *
     * @param object 对象
     * @return paramMap
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> fromObject(Object object) {
        if (object == null) {
            // 注意这里不能返回Collections.emptyList(), 因为后面该map在headers中会被插入
            return Maps.newHashMap();
        }

        final String nullText = "null";

        try {
            JSONObject jsonObject = JSONObject.parseObject(JacksonUtils.serialize(object));
            if (jsonObject == null || CollectionUtils.isEmpty(jsonObject.keySet())) {
                return Maps.newHashMap();
            }

            Map<String, String> params = Maps.newHashMapWithExpectedSize(jsonObject.keySet().size());
            jsonObject.keySet().stream()
                .filter(key -> StringUtils.isNotBlank(jsonObject.getString(String.valueOf(key)))
                    && !jsonObject.getString(String.valueOf(key)).equals(nullText))
                .forEach(key -> params.put(String.valueOf(key), jsonObject.getString(String.valueOf(key))));
            return params;
        } catch (Exception e) {
            logger.error("convert object to HttpParamMap error.", e);
            throw new EsportsClubException(UtilErrorCode.UNABLE_TO_CONVERT_AS_HTTP_PARAM).addErrorDescParam(object);
        }
    }
}
