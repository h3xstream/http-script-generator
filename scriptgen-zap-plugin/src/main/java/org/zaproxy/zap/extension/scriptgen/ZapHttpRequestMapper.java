package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import org.apache.commons.httpclient.URI;
import org.parosproxy.paros.network.HtmlParameter;
import org.parosproxy.paros.network.HttpMessage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ZapHttpRequestMapper {

    public static HttpRequestInfo buildRequestInfo(HttpMessage httpMessage) throws IOException {
        String method = httpMessage.getRequestHeader().getMethod();
        URI url = httpMessage.getRequestHeader().getURI();
        boolean isDefaultPort = url.getPort() == -1;

        String urlWithoutQuery = url.getScheme()+ "://"+url.getHost()+ //
                (isDefaultPort ? "": ":"+url.getPort())+ //
                url.getPath();

        Map<String,String> paramsGet = new HashMap<String,String>();
        for(HtmlParameter param : httpMessage.getUrlParams()) {
            paramsGet.put(URLDecoder.decode(param.getName(), "UTF-8"), URLDecoder.decode(param.getValue(), "UTF-8"));
        }

        Map<String,String> paramsPost = new HashMap<String, String>();
        for(HtmlParameter param : httpMessage.getFormParams()) {
            paramsPost.put(URLDecoder.decode(param.getName(), "UTF-8"), URLDecoder.decode(param.getValue(),"UTF-8"));
        }

        String postData = null;
        String requestBody = new String(httpMessage.getRequestBody().getBytes());
        if(requestBody.indexOf("=") == -1) { //No "=" is found in the body
            postData = requestBody;
            paramsPost = new HashMap<String, String>(); //Empty the post parameters
        }

        Map<String,String> headers = new HashMap<String, String>();
        String headerString = httpMessage.getRequestHeader().getHeadersAsString();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(headerString.getBytes())));
        String headerLine = null;
        while((headerLine = br.readLine()) != null) {
            String[] headerParts = headerLine.split(":", 2);
            if (headerParts.length == 2) {
                headers.put(headerParts[0].trim(), URLDecoder.decode(headerParts[1].trim(),"UTF-8"));
            }
        }

        return new HttpRequestInfo(method,urlWithoutQuery,paramsGet,paramsPost,postData,headers);
    }
}
