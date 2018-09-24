package com.jayzero.games.esportsclub.commonutils.constant;

/**
 * DateTimeConstants
 *
 * @author 成至
 * @version DateTimeConstants.java, v0.1
 * @date 2017/08/23
 */
public class DateTimeConstants {

    /**
     * 每秒的毫秒数
     */
    public static final long MILLIS_IN_SECOND = 1000;

    /**
     * 每天的毫秒数
     */
    public static final long MILLIS_IN_ONE_DAY = 86400000;

    /**
     * 标准Date格式, 精确到天, yyyy-MM-dd, 如2017-08-04
     */
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 紧凑Date格式, 精确到天, yyyyMMdd, 如20170804
     */
    public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";

    /**
     * 标准Datetime格式, 精确到秒
     */
    public static final String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 在标准Datetime格式基础上, 将天和时间之间的空格改用下划线连接, 精确到秒
     */
    public static final String STANDARD_DATETIME_FORMAT_CONCAT_WITH_UNDERSCORE = "yyyy-MM-dd_HH:mm:ss";

    /**
     * 标准时时间戳格式
     */
    public static final String STANDARD_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    /**
     * '/'分割的Date格式, 精确到天, yyyy/MM/dd, 如2017/08/04
     */
    public static final String SLASH_SEPARATED_DATE_FORMAT = "yyyy/MM/dd";

    /**
     * '/'分割的Date格式, 精确到秒, yyyy/MM/dd HH:mm:ss, 如2017/08/04 12:30:30
     */
    public static final String SLASH_SEPARATED_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

    /**
     * 允许的时间格式
     */
    public static final String[] ALLOWED_DATE_FORMATS = new String[] {
        COMPACT_DATE_FORMAT,
        STANDARD_DATE_FORMAT,
        STANDARD_DATETIME_FORMAT};

    /**
     * 宽松的允许的时间格式
     */
    public static final String[] LOOSE_ALLOWED_DATE_FORMATS = new String[] {
        COMPACT_DATE_FORMAT,
        STANDARD_DATE_FORMAT,
        STANDARD_DATETIME_FORMAT,
        SLASH_SEPARATED_DATE_FORMAT,
        SLASH_SEPARATED_DATETIME_FORMAT};
}
