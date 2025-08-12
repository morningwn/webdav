package com.github.morningwn.model;

import java.util.List;

/**
 * PROPFIND 返回体模型
 */
public class PropfindResponseBody {
    private List<ResourceResponse> responses;

    public List<ResourceResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<ResourceResponse> responses) {
        this.responses = responses;
    }

    public static class ResourceResponse {
        private String href;
        private Prop prop;
        private String status;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public Prop getProp() {
            return prop;
        }

        public void setProp(Prop prop) {
            this.prop = prop;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Prop {
        private String displayname;
        private Long getcontentlength;
        private String getlastmodified;

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }

        public Long getGetcontentlength() {
            return getcontentlength;
        }

        public void setGetcontentlength(Long getcontentlength) {
            this.getcontentlength = getcontentlength;
        }

        public String getGetlastmodified() {
            return getlastmodified;
        }

        public void setGetlastmodified(String getlastmodified) {
            this.getlastmodified = getlastmodified;
        }
    }
}

