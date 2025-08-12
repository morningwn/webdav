package com.github.morningwn.enums;

public enum WebdavMethodType {
    GET,
    PUT,
    POST,
    DELETE,
    MKCOL,
    COPY,
    MOVE,
    PROPFIND,
    PROPPATCH,
    LOCK,
    UNLOCK;

    public static WebdavMethodType fromString(String method) {
        try {
            return WebdavMethodType.valueOf(method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported WebDAV method: " + method);
        }
    }
}
