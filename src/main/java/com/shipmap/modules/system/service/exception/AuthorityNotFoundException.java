package com.shipmap.modules.system.service.exception;

/**
 * 功能信息不存在
 **/
public class AuthorityNotFoundException extends RuntimeException {
    public AuthorityNotFoundException() {
        super();
    }

    public AuthorityNotFoundException(String message) {
        super(message);
    }

    public AuthorityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorityNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AuthorityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
