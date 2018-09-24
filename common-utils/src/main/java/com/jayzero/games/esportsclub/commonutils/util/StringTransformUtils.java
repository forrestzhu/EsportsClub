package com.jayzero.games.esportsclub.commonutils.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StringTransformUtils
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version StringTransformUtils.java, v0.1
 * @date 2017/10/31
 */
public class StringTransformUtils {

    private static final Logger logger = LoggerFactory.getLogger(StringTransformUtils.class);

    /**
     * 将map转换为string
     *
     * @param map Map
     * @return string
     */
    public static <K, V> String map2String(Map<K, V> map) {
        return GsonUtils.serialize(map);
    }

    /**
     * trim字符串右边的空格
     *
     * @param stringToTrim 要被trim的字符串
     * @return string after trim
     */
    public static String rtrim(String stringToTrim) {
        if (StringUtils.isBlank(stringToTrim)) {
            return StringConstants.EMPTY;
        } else {
            return StringUtils.trim(StringConstants.UNDERSCORE + stringToTrim).substring(1);
        }
    }

    /**
     * 将byte数组转换为String
     *
     * @param byteArray         byte数组
     * @param characterEncoding 字符编码方式
     * @return byte数组对应的string
     */
    public static String byteArray2String(byte[] byteArray, @Nonnull String characterEncoding) {
        String result;
        if (byteArray.length > 0) {
            try {
                result = new String(byteArray, 0, byteArray.length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                logger.error("UnsupportedEncodingException occurred.", ex);
                result = null;
            }
        } else {
            result = null;
        }
        return result;
    }
}
