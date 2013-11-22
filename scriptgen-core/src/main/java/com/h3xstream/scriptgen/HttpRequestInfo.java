package com.h3xstream.scriptgen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This POJO permit abstraction from the initiator (Burp proxy or ZAP)
 */
public class HttpRequestInfo {

    private String method;
    private String url;
    private Map<String, String> parametersGet;
    private Map<String, String> parametersPost;
    private String postData;
    private Map<String, String> headers;
    private Map<String, String> cookies = new HashMap<String, String>();

    public HttpRequestInfo(String method, String url, Map<String, String> parametersGet, Map<String, String> parametersPost, Map<String, String> headers) {
        this.method = method;
        this.url = url;
        this.parametersGet = parametersGet;
        this.parametersPost = parametersPost;
        this.postData = null;
        this.headers = headers;

        extractHeaders();
        if(this.parametersGet.size() == 0) this.parametersGet = null;
        if(this.parametersPost.size() == 0) this.parametersPost = null;
        if(this.headers.size() == 0) this.headers = null;
    }

    private void extractHeaders() {

        for(Iterator<Map.Entry<String, String>> it = this.headers.entrySet().iterator(); it.hasNext(); ) {

            Map.Entry<String, String> entry = it.next();
            System.out.println(entry.getKey());

            //Host
            if(entry.getKey().toLowerCase().equals("host")) {
                it.remove();
            }

            //Cookies
            if(entry.getKey().toLowerCase().equals("cookie")) {
                String[] cookiesFound = entry.getValue().split(";");
                for(String cook : cookiesFound) {
                    String[] cookieParts = cook.split("=");
                    cookies.put(cookieParts[0].trim(),cookieParts[1].trim());
                }
                it.remove();
            }
        }

        if(cookies.size() == 0) cookies = null;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParametersGet() {
        return parametersGet;
    }

    public Map<String, String> getParametersPost() {
        return parametersPost;
    }

    public String getPostData() {
        return postData;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
