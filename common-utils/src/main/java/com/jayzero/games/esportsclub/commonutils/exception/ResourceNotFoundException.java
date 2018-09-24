package com.jayzero.games.esportsclub.commonutils.exception;

/**
 * ResourceNotFoundException
 *
 * @author 成至
 * @version ResourceNotFoundException.java, v0.1
 * @date 2017/08/15
 */
public class ResourceNotFoundException extends EsportsClubException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(ErrorCodeSupplier errorCodeSupplier) {
        super(errorCodeSupplier, 404);
    }

    public ResourceNotFoundException(ErrorCodeSupplier errorCodeSupplier, Throwable throwable) {
        super(errorCodeSupplier, throwable, 404);
    }

}
