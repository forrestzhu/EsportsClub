package com.jayzero.games.esportsclub.commonutils.exception;

/**
 * UnauthorizedException
 *
 * @author 成至
 * @version UnauthorizedException.java, v0.1
 * @date 2017/09/11
 */
public class UnauthorizedException extends EsportsClubException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(ErrorCodeSupplier errorCodeSupplier) {
        super(errorCodeSupplier, 401);
    }

    public UnauthorizedException(ErrorCodeSupplier errorCodeSupplier, Throwable throwable) {
        super(errorCodeSupplier, throwable, 401);
    }

}
