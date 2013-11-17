package com.h3xstream.scriptgen;

import java.util.Map;

/**
 * This POJO permit abstraction from the initiator (Burp proxy or ZAP)
 */
public class HttpRequestInfo {

    private final String method;
    private final String path;
    private final Map<String, String> getParameters;
    private final Map<String, String> postParameters;
    private final String postData;
    private final Map<String, String> headers;

    public HttpRequestInfo(String method, String path, Map<String, String> getParameters, Map<String, String> postParameters, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.getParameters = getParameters;
        this.postParameters = postParameters;
        this.postData = null;
        this.headers = headers;
    }
}
