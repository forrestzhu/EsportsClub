package com.jayzero.games.esportsclub.commonutils.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.jayzero.games.esportsclub.commonutils.exception.InvalidParameterException;
import com.jayzero.games.esportsclub.commonutils.exception.errorcode.UtilErrorCode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import static com.jayzero.games.esportsclub.commonutils.constant.DateTimeConstants.ALLOWED_DATE_FORMATS;
import static com.jayzero.games.esportsclub.commonutils.constant.DateTimeConstants.MILLIS_IN_ONE_DAY;
import static com.jayzero.games.esportsclub.commonutils.constant.DateTimeConstants.STANDARD_TIMESTAMP_FORMAT;
import static java.util.Calendar.DATE;

/**
 * DateTimeUtils
 *
 * @author 成至
 * @version DateTimeUtils.java, v0.1
 * @date 2017/08/18
 */
public class DateTimeUtils {

    /**
     * 将LocalDate类型转换为Date类型
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDateTime类型转换为Date类型
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将Date类型转换为LocalDate类型
     */
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 将Date类型转换为LocalDateTime类型
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 在date的基础上得到其后面daysToAdd天的新date
     *
     * @param date      初始date
     * @param daysToAdd 增加的天数
     * @return 增加days后的date
     */
    public static Date addDays(Date date, int daysToAdd) {
        return new Date(date.getTime() + daysToAdd * MILLIS_IN_ONE_DAY);
    }

    /**
     * 在date的基础上得到其后面daysToAdd天的新date
     *
     * @param date        初始date
     * @param daysToMinus 减少的天数
     * @return 减少days后的date
     */
    public static Date minusDays(Date date, int daysToMinus) {
        return new Date(date.getTime() - daysToMinus * MILLIS_IN_ONE_DAY);
    }

    /**
     * 把String类型的日期转成Date类型，默认支持ALLOWED_FORMAT
     *
     * @param dateStr 字符串类型的日期
     * @return 日期
     */
    public static Date string2Date(String dateStr) {
        try {
            return DateUtils.parseDate(dateStr, ALLOWED_DATE_FORMATS);
        } catch (ParseException e) {
            throw new InvalidParameterException(UtilErrorCode.DATE_BAD_FORMAT)
                .addErrorDescParam(dateStr);
        }
    }

    /**
     * 把String类型的日期转成Date类型
     *
     * @param dateStr  字符串类型的日期
     * @param patterns 支持的格式
     * @return 日期
     */
    public static Date string2Date(String dateStr, String... patterns) {
        try {
            return DateUtils.parseDate(dateStr, patterns);
        } catch (Exception e) {
            throw new InvalidParameterException(UtilErrorCode.DATE_BAD_FORMAT)
                .addErrorDescParam(dateStr);
        }
    }

    /**
     * 计算天数差,返回值可能为负数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 天数差, endDate-beginDate
     */
    public static int dayDiff(Date beginDate, Date endDate) {
        if (endDate.after(beginDate)) {
            long beginTimeMillis = DateUtils.truncate(beginDate, DATE).getTime();
            long endTimeMillis = DateUtils.ceiling(endDate, DATE).getTime();
            return (int)((endTimeMillis - beginTimeMillis) / MILLIS_IN_ONE_DAY) - 1;
        } else if (endDate.before(beginDate)) {
            return -dayDiff(endDate, beginDate);
        } else {
            return 0;
        }
    }

    /**
     * 计算时间单位差,返回值可能为负数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param timeUnit  时间单位
     * @return 时间单位差, endDate-beginDate
     */
    public static int periodDiff(Date beginDate, Date endDate, TimeUnit timeUnit) {
        final long millisForTimeUnit = timeUnit.toMillis(1);
        if (endDate.after(beginDate)) {
            long beginTimeMillis = beginDate.getTime();
            long endTimeMillis = endDate.getTime();
            return (int)((endTimeMillis - beginTimeMillis) / millisForTimeUnit) - 1;
        } else if (endDate.before(beginDate)) {
            return -dayDiff(endDate, beginDate);
        } else {
            return 0;
        }
    }

    /**
     * 时间戳转换为字符串
     *
     * @param date Date
     * @return STANDARD_TIMESTAMP_FORMAT格式的字符串
     */
    public static String timestampFormat(Date date) {
        return DateFormatUtils.format(date, STANDARD_TIMESTAMP_FORMAT);
    }

    /**
     * 将date类型转换为calendar类型
     *
     * @param date Date
     * @return Calendar
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取当前时间
     *
     * @param dateFormat dateFormat
     * @return String
     */
    public static String currentDate(String dateFormat) {
        return date2String(new Date(), dateFormat);
    }

    /**
     * date2string
     *
     * @param date       日期
     * @param dateFormat dateFormat
     * @return String
     */
    public static String date2String(Date date, String dateFormat) {
        return DateFormatUtils.format(date, dateFormat);
    }

    /**
     * 判断传入的时间是否比当前时间要早至少taskrunFinishTimeThresholdInMs毫秒
     *
     * @param time          需要进行判断的时间
     * @param thresholdInMs 比当前时间早的毫秒数
     * @return true if time is earlier than currentTime at least thresholdInMs milliSeconds, false otherwise
     */
    public static boolean timeEarlierThan(Date time, int thresholdInMs) {
        long gapTimeInMs = System.currentTimeMillis() - time.getTime();
        long longThresholdInMs = (long)thresholdInMs;
        return gapTimeInMs > longThresholdInMs;
    }
}
