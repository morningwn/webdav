package com.github.morningwn.method;

import com.github.morningwn.common.WebdavRequest;
import com.github.morningwn.common.WebdavResponse;
import com.github.morningwn.enums.WebdavMethodType;

public interface WebdavMethod {

    WebdavResponse handleRequest(WebdavRequest request);

    WebdavMethodType getType();
}
