package com.github.morningwn.model;

import java.util.List;

/**
 * PROPFIND 请求体模型
 */
public class PropfindRequestBody {
    private List<String> props;

    public List<String> getProps() {
        return props;
    }

    public void setProps(List<String> props) {
        this.props = props;
    }
}

