package com.github.morningwn.common;

import com.github.morningwn.enums.HttpStatusCode;
import lombok.Data;

import java.util.Map;

@Data
public class WebdavResponse {
    private HttpStatusCode statusCode;
    private Map<String, String> headers;
    private String body;

}

