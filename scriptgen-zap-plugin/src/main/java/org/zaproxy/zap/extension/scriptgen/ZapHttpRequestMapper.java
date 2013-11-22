package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.HttpRequestInfo;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.parosproxy.paros.network.HtmlParameter;
import org.parosproxy.paros.network.HttpMessage;

import java.util.HashMap;
import java.util.Map;

public class ZapHttpRequestMapper {

    public static HttpRequestInfo buildRequestInfo(HttpMessage httpMessage) throws URIException {
        String method = httpMessage.getRequestHeader().getMethod();
        URI url = httpMessage.getRequestHeader().getURI();
        String urlWithoutQuery = url.getScheme()+"://"+url.getHost()+url.getPath();

        Map<String,String> paramsGet = new HashMap<String,String>();
        for(HtmlParameter param : httpMessage.getUrlParams()) {
            paramsGet.put(param.getName(), param.getValue());
        }

        Map<String,String> paramsPost = new HashMap<String, String>();
        for(HtmlParameter param : httpMessage.getFormParams()) {
            paramsGet.put(param.getName(), param.getValue());
        }

        Map<String,String> headers = new HashMap<String, String>();

        return new HttpRequestInfo(method,urlWithoutQuery,paramsGet,paramsPost,headers);
    }
}
