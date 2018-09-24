package com.jayzero.games.esportsclub.commonutils.util;

import java.util.Collection;
import java.util.Objects;

import com.jayzero.games.esportsclub.commonutils.exception.InvalidParameterException;
import com.jayzero.games.esportsclub.commonutils.exception.errorcode.EsportsClubErrorCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * ParamValidator
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version ParamValidator.java, v0.1
 * @date 2017/10/02
 */
public class ParamValidator {

    /**
     * 验证参数不为null
     *
     * @param paramValue 任意Object
     * @param paramName  参数名称
     */
    public static void validateParamNotNull(Object paramValue, String paramName) {
        if (paramValue == null) {
            throw new InvalidParameterException(EsportsClubErrorCode.PARAMETER_EMPTY)
                .addErrorDescParam(paramName)
                .addTarget(paramName);
        }
    }

    /**
     * 验证参数等于某个值
     *
     * @param paramValue   参数的值
     * @param valueToEqual 要验证等于的值
     * @param paramName    参数名称
     */
    public static void validateParamEquals(Object paramValue, Object valueToEqual, String paramName) {
        if (Objects.equals(paramValue, valueToEqual)) {
            throw new InvalidParameterException(EsportsClubErrorCode.INVALID_PARAMETER_ERROR).addTarget(paramName);
        }
    }

    /**
     * 验证String类型的参数不为空
     *
     * @param paramValue 参数值
     * @param paramName  参数名称
     */
    public static void validateParamNotBlank(String paramValue, String paramName) {
        if (StringUtils.isBlank(paramValue)) {
            throw new InvalidParameterException(EsportsClubErrorCode.PARAMETER_EMPTY)
                .addErrorDescParam(paramName)
                .addTarget(paramName);
        }
    }

    /**
     * 验证集合的大小不为空
     *
     * @param paramValue 参数值
     * @param paramName  参数名称
     */
    public static void validateParamNotEmpty(final Collection<?> paramValue, String paramName) {
        if (CollectionUtils.isEmpty(paramValue)) {
            throw new InvalidParameterException(EsportsClubErrorCode.PARAMETER_EMPTY)
                .addErrorDescParam(paramName)
                .addTarget(paramName);
        }
    }

    /**
     * 验证集合的数目不超过某个限制的值
     *
     * @param paramValue 参数值
     * @param paramName  参数名称
     * @param maxLimit   最大限制条数
     */
    public static void validateParamSizeNotExceedsLimit(final Collection<?> paramValue, String paramName,
        int maxLimit) {
        if (paramValue != null && paramValue.size() > maxLimit) {
            throw new InvalidParameterException(EsportsClubErrorCode.PARAMETER_SIZE_EXCEEDS_LIMIT)
                .addErrorDescParam(paramName)
                .addErrorDescParam(maxLimit)
                .addTarget(paramName);
        }
    }

    /**
     * 验证集合的数目大于或等于某个阈值
     *
     * @param paramValue   参数值
     * @param paramName    参数名称
     * @param minThreshold 最小限制阈值, 如果是0, 就不校验
     */
    public static void validateParamSizeGreaterOrEqualToThreshold(final Collection<?> paramValue, String paramName,
        int minThreshold) {
        if (minThreshold > 0) {
            if (CollectionUtils.isEmpty(paramValue) || paramValue.size() < minThreshold) {
                throw new InvalidParameterException(EsportsClubErrorCode.PARAMETER_SIZE_LESS_THAN_THRESHOLD)
                    .addErrorDescParam(paramName)
                    .addErrorDescParam(minThreshold)
                    .addTarget(paramName);
            }
        }
    }
}
