package burp;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.model.MultiPartParameter;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BurpHttpRequestMapper {

    public static List<HttpRequestInfo> buildListRequestInfo(IHttpRequestResponse[] requestsSelected, IExtensionHelpers helpers) {

        List<HttpRequestInfo> requests = new ArrayList<>();

        for (IHttpRequestResponse req : requestsSelected) {
            requests.add(buildSingleRequestInfo(req,helpers));
        }
        return requests;
    }

    private static HttpRequestInfo buildSingleRequestInfo(IHttpRequestResponse req, IExtensionHelpers helpers) {
        byte[] requestBytes = req.getRequest();
        IRequestInfo requestInfo = helpers.analyzeRequest(req.getHttpService(), requestBytes);

        URL url = requestInfo.getUrl();

        //Headers
        boolean isMultipart = false;
        Map<String, String> headers = new HashMap<String, String>();

        int i = 0;
        for (String header : requestInfo.getHeaders()) {
            if (i++ == 0) continue; //Skip the first line

            String[] headerParts = header.split(":", 2);
            if (headerParts.length == 2) {
                String name = headerParts[0].trim();
                String value = headerParts[1].trim();
                if(name.toLowerCase().equals("content-type") && value.toLowerCase().startsWith("multipart")) {
                    isMultipart = true;
                }

                headers.put(name, value);
            }
        }


        //Build the map of parameters
        Map<String, String> paramsGet = new HashMap<String, String>();
        Map<String, String> paramsPost = new HashMap<String, String>();
        List<MultiPartParameter> multiPartParameters = new ArrayList<MultiPartParameter>();

        boolean hasJsonBody = false;
        Iterator<IParameter> iterator = requestInfo.getParameters().iterator();
        while(iterator.hasNext()) {
            IParameter param = iterator.next();
            try {
                Log.debug(String.format("Parameter: type='%s' name='%s' value='%s[..]'",
                        paramTypeToString(param.getType()),
                        param.getName(),
                        param.getValue().substring(0,Math.min(param.getValue().length(), 15))));
                if (param.getType() == IParameter.PARAM_URL) {

                    paramsGet.put(param.getName(), URLDecoder.decode(param.getValue(), "UTF-8"));

                } else if (param.getType() == IParameter.PARAM_BODY) {
                    paramsPost.put(param.getName(), URLDecoder.decode(param.getValue(), "UTF-8"));
                } else if (param.getType() == IParameter.PARAM_MULTIPART_ATTR) {
                    IParameter paramBody = iterator.next(); //The multi-part parameter are split in two
                    multiPartParameters.add(new MultiPartParameter(paramBody.getName(),paramBody.getValue(),
                            guessContentType(param.getValue()),param.getValue()));
                }
                else  if (param.getType() == IParameter.PARAM_JSON) {
                    hasJsonBody = true;
                }
            } catch (UnsupportedEncodingException e) {
                Log.error("Error while building request entity for '"+url.toString()+"': "+e.getMessage());
                throw new RuntimeException(e);
            }
        }

        //POST data
        byte[] requestBodyBytes = new byte[requestBytes.length - requestInfo.getBodyOffset()];
        System.arraycopy(requestBytes, requestInfo.getBodyOffset(), requestBodyBytes, 0, requestBytes.length - requestInfo.getBodyOffset());
        String requestBodyString = new String(requestBodyBytes);
        if (requestBodyString.indexOf("=") == -1 && paramsPost.size() > 0) { //Not a POST request
            //In the case of RAW body (json, binary, etc.)
            paramsPost = new HashMap<String, String>();
            if("".equals(requestBodyString)) {
                requestBodyString = null;
            }
        }
        else if(hasJsonBody) { //JSON body
            //requestBodyString is kept as-is
        }
        else {
            requestBodyString = null;
        }



        return new HttpRequestInfo(requestInfo.getMethod(), url.toString(), paramsGet, paramsPost, requestBodyString,
                headers,multiPartParameters);
    }

    private static String paramTypeToString(byte value) {
        switch (value) {
            case IParameter.PARAM_MULTIPART_ATTR:
                return "MULTIPART_ATTR";
            case IParameter.PARAM_BODY:
                return "BODY";
            case IParameter.PARAM_COOKIE:
                return "COOKIE";
            case IParameter.PARAM_JSON:
                return "JSON";
            case IParameter.PARAM_URL:
                return "URL";
            case IParameter.PARAM_XML:
                return "XML";
            case IParameter.PARAM_XML_ATTR:
                return "XML_ATTR";
            default:
                return "UNKNOWN";
        }
    }

    private static String guessContentType(String fileName) {
        if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if(fileName.endsWith(".gif")) {
            return "image/gif";
        }
        if(fileName.endsWith(".png")) {
            return "application/png";
        }
        if(fileName.endsWith(".zip")) {
            return "application/zip";
        }
        if(fileName.endsWith(".xml")) {
            return "application/xml";
        }
        return "application/octet-stream";
    }
}
