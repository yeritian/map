package com.shipmap.framework.service.exception;

/**
 * 认证失败异常
 **/
public class AuthFailException extends RuntimeException {
    private static final long serialVersionUID = 5853589848123857482L;

    public AuthFailException() {
        super();
    }

    public AuthFailException(String message) {
        super(message);
    }

    public AuthFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthFailException(Throwable cause) {
        super(cause);
    }

    protected AuthFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
