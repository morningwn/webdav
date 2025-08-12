package com.github.morningwn.common;

import lombok.Data;

import java.util.Map;

@Data
public class WebdavRequest {
    private String path;
    private String method;
    private Map<String, String> headers;
    private String body;
}

