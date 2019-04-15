package com.shipmap.modules.system.service.exception;

/**
 * 菜单功能下有子节点
 **/
public class AuthorityHaveChildNodesException extends RuntimeException {
    public AuthorityHaveChildNodesException() {
        super();
    }

    public AuthorityHaveChildNodesException(String message) {
        super(message);
    }

    public AuthorityHaveChildNodesException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorityHaveChildNodesException(Throwable cause) {
        super(cause);
    }

    protected AuthorityHaveChildNodesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
