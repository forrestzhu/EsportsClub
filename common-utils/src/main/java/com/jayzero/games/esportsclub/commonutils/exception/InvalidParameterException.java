package com.jayzero.games.esportsclub.commonutils.exception;

/**
 * InvalidParameterException
 *
 * @author 成至
 * @version InvalidParameterException.java, v0.1
 * @date 2017/08/15
 */
public class InvalidParameterException extends EsportsClubException {

    private static final long serialVersionUID = 1L;

    public InvalidParameterException(ErrorCodeSupplier errorCodeSupplier) {
        super(errorCodeSupplier, 400);
    }

    public InvalidParameterException(ErrorCodeSupplier errorCodeSupplier, Throwable throwable) {
        super(errorCodeSupplier, throwable, 400);
    }
}
