package com.jayzero.games.esportsclub.commonutils.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * StorageUnitCalculator
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version StorageUnitCalculator.java, v0.1
 * @date 2018/06/15
 */
public class StorageUnitCalculator {

    /**
     * 可读的存储的正则表达式
     */
    private static final String REGEX_FOR_READABLE_STORAGE = "([\\d.]+)([PTGMK]B)?";

    /**
     * 可读存储的pattern
     */
    private static final Pattern READABLE_STORAGE_PATTERN = Pattern.compile(REGEX_FOR_READABLE_STORAGE,
        Pattern.CASE_INSENSITIVE);

    /**
     * 将可读的storageSize转换为想要的存储单位的数值
     *
     * @param storageAsString          可读的storageSize
     * @param defaultSourceStorageUnit storageAsString的默认单位,
     *                                 如果storageAsString本身就是数字, 则认为其单位就是defaultSourceStorageUnit
     * @param targetStorageUnit        想要转化的存储单位的数值
     * @return storageSize in targetStorageUnit
     */
    public static long convertReadableStorageByDesiredUnit(String storageAsString, StorageUnit defaultSourceStorageUnit,
        StorageUnit targetStorageUnit) {

        // 默认精度为四舍五入
        return convertReadableStorageByDesiredUnit(storageAsString, defaultSourceStorageUnit, targetStorageUnit,
            PrecisionType.ROUND);
    }

    /**
     * 将可读的storageSize转换为想要的存储单位的数值
     *
     * @param storageAsString          可读的storageSize
     * @param defaultSourceStorageUnit storageAsString的默认单位,
     *                                 如果storageAsString本身就是数字, 则认为其单位就是defaultSourceStorageUnit
     * @param targetStorageUnit        想要转化的存储单位的数值
     * @return storageSize in targetStorageUnit
     */
    public static long convertReadableStorageByDesiredUnit(String storageAsString, StorageUnit defaultSourceStorageUnit,
        StorageUnit targetStorageUnit, PrecisionType precisionType) {

        ParamValidator.validateParamNotBlank(storageAsString, "storageAsString");
        double storageSizeInDouble = convertReadableStorageAsDoubleByDesiredUnit(storageAsString,
            defaultSourceStorageUnit, targetStorageUnit);

        // 设置精度类型默认为四舍五入
        precisionType = precisionType == null ? PrecisionType.ROUND : precisionType;

        switch (precisionType) {
            case ROUND:
                return Math.round(storageSizeInDouble);
            case CEIL:
                return (long)Math.ceil(storageSizeInDouble);
            case FLOOR:
                return (long)Math.floor(storageSizeInDouble);
            default:
                throw new IllegalStateException(String.format("Unexpected precisionType[%s] found.", precisionType));
        }
    }

    /**
     * 将可读的storageSize转换为想要的存储单位的数值
     *
     * @param storageAsString          可读的storageSize
     * @param defaultSourceStorageUnit storageAsString的默认单位,
     *                                 如果storageAsString本身就是数字, 则认为其单位就是defaultSourceStorageUnit
     * @param targetStorageUnit        想要转化的存储单位的数值
     * @return storageSize in targetStorageUnit
     */
    public static double convertReadableStorageAsDoubleByDesiredUnit(String storageAsString,
        StorageUnit defaultSourceStorageUnit, StorageUnit targetStorageUnit) {

        ParamValidator.validateParamNotBlank(storageAsString, "storageAsString");

        // defaultSourceStorageUnit以及targetStorageUnit的默认值都设置为StorageUnit.B
        defaultSourceStorageUnit = defaultSourceStorageUnit == null ? StorageUnit.B : defaultSourceStorageUnit;
        targetStorageUnit = targetStorageUnit == null ? StorageUnit.B : targetStorageUnit;

        // 1. 将字符串截取为数字和单位两部分
        // 将逗号和空格都去掉
        storageAsString = storageAsString.replaceAll("\\s|,", "");

        Matcher matcher = READABLE_STORAGE_PATTERN.matcher(storageAsString);
        if (matcher.matches()) {
            // 2. 从单位中解析出对应的单位
            StorageUnit storageUnit = defaultSourceStorageUnit;
            if (StringUtils.isNotBlank(matcher.group(Digits.TWO))) {
                storageUnit = StorageUnit.fromName(matcher.group(Digits.TWO).toUpperCase());
            }

            String number = matcher.group(Digits.ONE);
            int sourcePow = storageUnit.getPowerOf1024();

            // 3. 将数字根据单位转换为以targetStorageUnit计的数字, 如果传入是空, 则认为是Byte单位
            int targetPow = targetStorageUnit.getPowerOf1024();
            BigDecimal bytes = new BigDecimal(number);
            if (sourcePow - targetPow >= 0) {
                bytes = bytes.multiply(BigDecimal.valueOf(Digits.INT_1024).pow(sourcePow - targetPow));
            } else {
                bytes = bytes.multiply(BigDecimal.valueOf(1.0 / Digits.INT_1024).pow(targetPow - sourcePow));
            }
            double result = bytes.doubleValue();
            return Math.ceil(result);
        } else {
            throw new IllegalStateException(String.format("invalid storageAsString [%s]", storageAsString));
        }
    }
}
