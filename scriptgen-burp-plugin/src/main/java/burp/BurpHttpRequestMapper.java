package burp;

import com.h3xstream.scriptgen.model.HttpRequestInfo;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class BurpHttpRequestMapper {
    public static HttpRequestInfo buildRequestInfo(IRequestInfo requestInfo,byte[] responseBytes) {
        URL url = requestInfo.getUrl();
        boolean isDefaultPort = url.getPort() == -1;

        String urlWithoutQuery = url.getProtocol()+ "://"+url.getHost()+ //
                (isDefaultPort ? "": ":"+url.getPort())+ //
                url.getPath();

        //Build the map of parameters
        Map<String, String> paramsGet = new HashMap<String, String>();
        Map<String, String> paramsPost = new HashMap<String, String>();

        for (IParameter param : requestInfo.getParameters()) {
            try {
                if (param.getType() == IParameter.PARAM_URL) {

                    paramsGet.put(param.getName(), URLDecoder.decode(param.getValue(), "UTF-8"));

                } else if (param.getType() == IParameter.PARAM_BODY) {
                    paramsPost.put(param.getName(), URLDecoder.decode(param.getValue(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        //POST data
        byte[] requestBodyBytes = new byte[responseBytes.length - requestInfo.getBodyOffset()];
        System.arraycopy(responseBytes, requestInfo.getBodyOffset(), requestBodyBytes, 0, responseBytes.length - requestInfo.getBodyOffset());

        //Headers
        Map<String, String> headers = new HashMap<String, String>();

        int i = 0;
        for (String header : requestInfo.getHeaders()) {
            if (i++ == 0) continue; //Skip the first line

            String[] headerParts = header.split(":", 2);
            if (headerParts.length == 2) {
                headers.put(headerParts[0].trim(), headerParts[1].trim());
            }
        }

        return new HttpRequestInfo(requestInfo.getMethod(), urlWithoutQuery, paramsGet, paramsPost, new String(requestBodyBytes), headers);
    }
}
