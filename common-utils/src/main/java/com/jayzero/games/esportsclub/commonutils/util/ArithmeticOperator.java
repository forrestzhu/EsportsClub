package com.jayzero.games.esportsclub.commonutils.util;

/**
 * ArithmeticOperator
 *
 * @author 成至
 * @date 2017/9/27
 */
public enum ArithmeticOperator {

    /**
     * 加
     */
    ADD('+'),

    /**
     * 减
     */
    SUBTRACT('-'),

    /**
     * 乘
     */
    MULTIPLY('*'),

    /**
     * 除
     */
    DIVIDE('/'),
    ;

    public final char name;

    ArithmeticOperator(char name) {
        this.name = name;
    }
}
