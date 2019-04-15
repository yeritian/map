package com.shipmap.framework.service.exception;

/**
 * 用户禁止登陆
 **/
public class UserDisabledException extends RuntimeException {
    private static final long serialVersionUID = -7337274693206264732L;

    public UserDisabledException() {
        super();
    }

    public UserDisabledException(String message) {
        super(message);
    }

    public UserDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDisabledException(Throwable cause) {
        super(cause);
    }

    protected UserDisabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
