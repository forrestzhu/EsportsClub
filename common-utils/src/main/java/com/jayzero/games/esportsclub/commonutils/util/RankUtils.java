package com.jayzero.games.esportsclub.commonutils.util;

/**
 * DateTimeUtils
 *
 * @author 成至
 * @version DateTimeUtils.java, v0.1
 * @date 2017/08/18
 */
public class RankUtils {

    private static final String FIRST = "1st";
    private static final String SECOND = "2nd";
    private static final String THIRD = "3rd";
    private static final String RANK_POSTFIX = "th";

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    /**
     * 将数字转换为rank
     */
    public static String numberToRankText(Number number) {
        if (number.intValue() == ONE) {
            return FIRST;
        } else if (number.intValue() == TWO) {
            return SECOND;
        } else if (number.intValue() == THREE) {
            return THIRD;
        } else {
            return String.format("%d%s", number.intValue(), RANK_POSTFIX);
        }
    }

}
