package com.h3xstream.scriptgen;

import java.util.Map;

/**
 * This POJO permit abstraction from the initiator (Burp proxy or ZAP)
 */
public class HttpRequestInfo {

    private final String method;
    private final String url;
    private final Map<String, String> getParameters;
    private final Map<String, String> postParameters;
    private final String postData;
    private final Map<String, String> headers;

    public HttpRequestInfo(String method, String url, Map<String, String> getParameters, Map<String, String> postParameters, Map<String, String> headers) {
        this.method = method;
        this.url = url;
        this.getParameters = getParameters;
        this.postParameters = postParameters;
        this.postData = null;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getGetParameters() {
        return getParameters;
    }

    public Map<String, String> getPostParameters() {
        return postParameters;
    }

    public String getPostData() {
        return postData;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
