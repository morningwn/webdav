package com.github.morningwn.method;

import com.github.morningwn.enums.WebdavMethodType;

public interface WebdavMethod {

    Object handleRequest(Object request);

    WebdavMethodType getType();
}
