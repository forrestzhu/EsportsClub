package com.jayzero.games.esportsclub.commonutils.util;

import java.util.Objects;

import org.springframework.util.Assert;

/**
 * MathUtils
 *
 * @author 成至
 * @version MathUtils.java, v0.1
 * @date 2017/08/24
 */
public class MathUtils {

    /**
     * x/y如果没有余数则直接返回结果,否则返回x/y+1
     *
     * @param x the dividend 大于0
     * @param y the divisor  大于0
     * @return x/y如果没有余数则直接返回结果,否则返回x/y+1
     */
    public static int ceilDiv(int x, int y) {
        Assert.isTrue(x > 0 && y > 0, " 只接受除数和被除数均为正数的情况");

        int r = x / y;
        // if modulo not zero, add result by 1
        if ((r * y != x)) {
            r++;
        }
        return r;
    }

    /**
     * 判断int是否为null或0
     *
     * @param integer 整数
     * @return true if null or zero, false otherwise
     */
    public static boolean nullOrZero(Integer integer) {
        return Objects.isNull(integer) || Objects.equals(integer, 0);
    }
}
