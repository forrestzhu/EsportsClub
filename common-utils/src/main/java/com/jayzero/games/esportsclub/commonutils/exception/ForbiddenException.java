package com.jayzero.games.esportsclub.commonutils.exception;

/**
 * ForbiddenException
 *
 * @author 成至
 * @version ForbiddenException.java, v0.1
 * @date 2017/09/11
 */
public class ForbiddenException extends EsportsClubException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException(ErrorCodeSupplier errorCodeSupplier) {
        super(errorCodeSupplier, 403);
    }

    public ForbiddenException(ErrorCodeSupplier errorCodeSupplier, Throwable throwable) {
        super(errorCodeSupplier, throwable, 403);
    }

}
