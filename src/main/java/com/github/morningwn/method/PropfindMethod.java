package com.github.morningwn.method;

import com.github.morningwn.common.WebdavRequest;
import com.github.morningwn.common.WebdavResponse;
import com.github.morningwn.enums.WebdavMethodType;

public class PropfindMethod implements WebdavMethod {

    @Override
    public WebdavResponse handleRequest(WebdavRequest request) {
        WebdavResponse response = new WebdavResponse();
        return response;
    }

    @Override
    public WebdavMethodType getType() {
        return WebdavMethodType.PROPFIND;
    }
}
