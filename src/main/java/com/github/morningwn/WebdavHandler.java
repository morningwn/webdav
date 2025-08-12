package com.github.morningwn;

import com.github.morningwn.common.WebdavRequest;
import com.github.morningwn.common.WebdavResponse;
import com.github.morningwn.enums.WebdavMethodType;
import com.github.morningwn.method.WebdavMethod;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class WebdavHandler {
    private final Map<WebdavMethodType, WebdavMethod> methodMap;

    public WebdavHandler(List<WebdavMethod> methods) {
        this.methodMap = new EnumMap<>(WebdavMethodType.class);
        for (WebdavMethod method : methods) {
            if (methodMap.containsKey(method.getType())) {
                throw new IllegalArgumentException("Duplicate WebDAV method: " + method.getType());
            }
            methodMap.put(method.getType(), method);
        }
    }

    public WebdavResponse handleRequest(WebdavRequest request) {
        WebdavMethodType methodType = WebdavMethodType.fromString(request.getMethod());
        WebdavMethod method = methodMap.get(methodType);
        if (method == null) {
            throw new IllegalArgumentException("Unsupported WebDAV method: " + methodType);
        }
        return method.handleRequest(request);
    }

}
