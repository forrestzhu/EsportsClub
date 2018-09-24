package com.jayzero.games.esportsclub.commonutils.exception;

/**
 * ErrorCodeSupplier
 *
 * @author 成至
 * @version ErrorCodeSupplier.java, v0.1
 * @date 2017/08/07
 */
public interface ErrorCodeSupplier {

    /**
     * 转换为ErrorCode
     *
     * @return ErrorCode
     */
    ErrorCode toErrorCode();
}