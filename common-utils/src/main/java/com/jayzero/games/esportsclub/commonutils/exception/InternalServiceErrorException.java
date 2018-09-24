package com.jayzero.games.esportsclub.commonutils.exception;

/**
 * 内部服务异常
 *
 * @author 成至
 * @date 2018/9/24
 */
public class InternalServiceErrorException extends EsportsClubException {

    private static final long serialVersionUID = 1L;

    public InternalServiceErrorException(ErrorCodeSupplier errorCodeSupplier) {
        super(errorCodeSupplier, 500);
    }

    public InternalServiceErrorException(ErrorCodeSupplier errorCodeSupplier, Throwable throwable) {
        super(errorCodeSupplier, throwable, 500);
    }

}
