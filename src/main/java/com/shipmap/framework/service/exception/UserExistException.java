package com.shipmap.framework.service.exception;

/**
 * 用户以存在异常
 **/
public class UserExistException extends RuntimeException {
    private static final long serialVersionUID = -2920236653459143230L;

    public UserExistException() {
        super();
    }

    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistException(Throwable cause) {
        super(cause);
    }

    protected UserExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
