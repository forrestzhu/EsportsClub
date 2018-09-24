package com.jayzero.games.esportsclub.commonutils.exception;

/**
 * MethodNotImplementedException
 *
 * @author 成至
 * @version MethodNotImplementedException.java, v0.1
 * @date 2017/08/15
 */
public class MethodNotImplementedException extends EsportsClubException {
    private static final long serialVersionUID = 1L;

    public MethodNotImplementedException(ErrorCodeSupplier errorCodeSupplier) {
        super(errorCodeSupplier, 501);
    }

    public MethodNotImplementedException(ErrorCodeSupplier errorCodeSupplier, Throwable throwable) {
        super(errorCodeSupplier, throwable, 500);
    }
}
