package com.jayzero.games.esportsclub.commonutils.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * ParamTransferUtils 用于转换map类型的param和string
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version ParamTransferHelper.java, v0.1
 * @date 2017/09/25
 */
public class ParamTransferUtils {

    private static final char EQUALS = '=';

    private static final char SPACE = ' ';

    /**
     * 将string类型的参数转换为参数map
     *
     * @param separatedParams 分割的参数
     * @param separator       分隔符
     * @return map
     */
    public static LinkedHashMap<String, String> string2Map(String separatedParams, char separator) {
        if (StringUtils.isBlank(separatedParams)) {
            return Maps.newLinkedHashMap();
        }
        String[] paramKvArray = StringUtils.split(separatedParams, separator);
        LinkedHashMap<String, String> params = Maps.newLinkedHashMapWithExpectedSize(paramKvArray.length);
        Arrays.stream(paramKvArray)
            .filter(StringUtils::isNotBlank)
            .forEach(
                paramKv -> {
                    int equalIndex = paramKv.indexOf(EQUALS);
                    // =必须存在, 且其位置不能在首尾两端, 即必须有key和value
                    Assert.isTrue(equalIndex > 0 && equalIndex < paramKv.length() - 1,
                        String.format("参数配置有误, 当前参数为{%s}, 格式应该是[param1=value1%s param2=value2]",
                            separatedParams, separator));
                    params.put(paramKv.substring(0, equalIndex), paramKv.substring(equalIndex + 1));
                }
            );
        return params;
    }

    /**
     * 将map类型的参数转换为字符隔开的string
     *
     * @param params    map参数
     * @param separator 分隔符
     * @return separator分割的参数字符串
     */
    @Nullable
    public static String map2String(Map<String, String> params, char separator) {
        if (MapUtils.isEmpty(params)) {
            return null;
        }
        StringBuilder paramBuilder = new StringBuilder();
        params.forEach((key, value) ->
            paramBuilder.append(key).append(EQUALS).append(value).append(separator));

        // 注意转换为string的时候需要将最后的separator去掉
        return paramBuilder.substring(0, paramBuilder.length() - 1);
    }

}
