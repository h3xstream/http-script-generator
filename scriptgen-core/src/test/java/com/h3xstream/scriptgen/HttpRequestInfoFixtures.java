package com.h3xstream.scriptgen;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.model.MultiPartParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestInfoFixtures {

    static Map<String, String> getParams = new HashMap<String, String>();
    static {
        getParams.put("id", "12345");
        getParams.put("hash", "3012451c92c89ed9b48dcdc817d6a527");
    }
    static Map<String, String> postParams = new HashMap<String, String>();
    static {
        postParams.put("username", "admin");
        postParams.put("password", "admin'--**\n\\");
        postParams.put("bug18[test]", "abcdef"); //Use to test fallback with Python Requests
    }
    static List<MultiPartParameter> multipartParams = new ArrayList<MultiPartParameter>();
    static {
        multipartParams.add(new MultiPartParameter("file1", "This is a test!","text/plain","test.txt"));
        multipartParams.add(new MultiPartParameter("file2", "PNG1234567890","image/png","test.png"));
    }
    static Map<String, String> headers = new HashMap<String, String>();
    static {
        headers.put("Cookie", "SID=Aualz4Rx0_8t3GJda; LANG=en-US");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");
        headers.put("Accept", "*/*");
    }

    public static HttpRequestInfo getGetRequest() {
        return new HttpRequestInfo("GET", "https://httpbin.org/get?a=b", getParams, null, null, headers, null);
    }

    public static HttpRequestInfo getPostRequest() {
        return new HttpRequestInfo("POST", "http://httpbin.org/post?a=1", getParams, postParams, null, headers, null);
    }

    public static HttpRequestInfo getPostMultiPartRequest() {
        return new HttpRequestInfo("POST", "http://httpbin.org/post?test=1", getParams, null, null, headers, multipartParams);
    }

    public static HttpRequestInfo getBrokenRequest() {
        return new HttpRequestInfo("SPECIAL\"')(\\", "http://httpbin.org/brokentest=1${php}#{ruby}\"')(aaaaaaaa\\", getParams, null, null, headers, multipartParams);
    }
}
