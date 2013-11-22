package burp;

import com.h3xstream.scriptgen.HttpRequestInfo;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BurpHttpRequestMapper {
    public static HttpRequestInfo buildRequestInfo(IRequestInfo requestInfo) {
        URL url = requestInfo.getUrl();
        String urlWithoutQuery = url.getProtocol()+"://"+url.getHost()+url.getPath();

        //Build the map of parameters
        Map<String,String> paramsGet = new HashMap<String,String>();
        Map<String,String> paramsPost = new HashMap<String,String>();

        for(IParameter param : requestInfo.getParameters()) {
            if(param.getType() == IParameter.PARAM_URL) {
                paramsGet.put(param.getName(),param.getValue());
            }
            else if(param.getType() == IParameter.PARAM_BODY) {
                paramsPost.put(param.getName(),param.getValue());
            }
        }

        //Headers
        Map<String,String> headers = new HashMap<String,String>();

        int i=0;
        for(String header : requestInfo.getHeaders()) {
            if(i++ == 0) continue; //Skip the first line

            String[] headerParts = header.split(":",2);
            if(headerParts.length == 2) {
                headers.put(headerParts[0].trim(), headerParts[1].trim());
            }
        }

        return new HttpRequestInfo(requestInfo.getMethod(),urlWithoutQuery,paramsGet,paramsPost,headers);
    }
}
