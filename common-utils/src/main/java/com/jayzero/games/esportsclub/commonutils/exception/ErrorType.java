package com.jayzero.games.esportsclub.commonutils.exception;

/**
 * ErrorType
 *
 * @author 成至
 * @version ErrorType.java, v0.1
 * @date 2017/08/07
 */
public enum ErrorType {

    /**
     * USER_ERROR表示用户错误
     */
    USER_ERROR,

    /**
     * INTERNAL_ERROR表示内部错误
     */
    INTERNAL_ERROR,

    /**
     * REMOTE_ERROR表示外部调用错误
     */
    REMOTE_ERROR,

    /**
     * EXTERNAL指外部错误
     */
    EXTERNAL
}