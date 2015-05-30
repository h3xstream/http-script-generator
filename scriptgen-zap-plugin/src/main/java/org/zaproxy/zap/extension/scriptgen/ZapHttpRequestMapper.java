package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.model.MultiPartParameter;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.httpclient.URI;
import org.parosproxy.paros.network.HtmlParameter;
import org.parosproxy.paros.network.HttpMessage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZapHttpRequestMapper {


    //Sample : multipart/form-data; boundary=---------------------------297592997116592
    private static final Pattern PATTERN_MULTIPART_FORM_BOUNDARY = Pattern.compile("multipart/form-data; boundary=(.*)");
    //Content-Disposition: form-data; name="numitems"
    private static final Pattern PATTERN_MULTIPART_PARAM_NAME = Pattern.compile("[^e]name=\"([^\"]+)\"");
    //Content-Disposition: form-data; name="uploadname2"; filename=""
    private static final Pattern PATTERN_MULTIPART_PARAM_FILENAME = Pattern.compile("filename=\"([^\"]+)\"");

    private static final Pattern PATTERN_MULTIPART_PARAM_CONTENT_TYPE = Pattern.compile("[Cc]ontent-[tT]ype: ([^\n^\r]+)");



    public static HttpRequestInfo buildRequestInfo(HttpMessage httpMessage) throws IOException {
        String method = httpMessage.getRequestHeader().getMethod();
        URI url = httpMessage.getRequestHeader().getURI();
        boolean isDefaultPort = url.getPort() == -1 || (url.getPort() == 443 && url.getScheme().equals("https")) || (url.getPort() == 80 && url.getScheme().equals("http"));

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
            if("".equals(postData)) {
                postData = null;
            }
        }

        String multiPartBoundary = null;

        Map<String,String> headers = new HashMap<String, String>();
        String headerString = httpMessage.getRequestHeader().getHeadersAsString();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(headerString.getBytes())));
        String headerLine = null;
        while((headerLine = br.readLine()) != null) {
            String[] headerParts = headerLine.split(":", 2);
            if (headerParts.length == 2) {

                String name = headerParts[0].trim();
                String value = URLDecoder.decode(headerParts[1].trim(),"UTF-8"); //Decode for cookies
                headers.put(name, value);

                if (name.toLowerCase().equals("content-type") && value.startsWith("multipart/form-data")) {
                    Matcher m = PATTERN_MULTIPART_FORM_BOUNDARY.matcher(value);
                    if(m.find()) {
                        multiPartBoundary = m.group(1);
                    }
                }
            }
        }

        List<MultiPartParameter> multiPartParameters = new ArrayList<MultiPartParameter>();
        if(multiPartBoundary != null) { //Boundary was intercept during the previous step (header parsing)
            parseMultiPartParameter(httpMessage.getRequestBody().getBytes(),multiPartBoundary.getBytes(), multiPartParameters, paramsPost);
        }

        return new HttpRequestInfo(method,urlWithoutQuery,paramsGet,paramsPost,postData,headers,multiPartParameters);
    }

    /**
     *
     * Use the low-level api of Commons-FileUpload to parse the different parameters.
     *
     * @param input
     * @param boundary
     * @param multiPartParameters
     * @param paramsPost
     */
    private static void parseMultiPartParameter(byte[] input,byte[] boundary,List<MultiPartParameter> multiPartParameters,Map<String,String> paramsPost) {

        try {
            MultipartStream multipartStream = new MultipartStream(new ByteArrayInputStream(input), boundary);
            boolean nextPart = multipartStream.skipPreamble();

            while(nextPart) {
                String header = multipartStream.readHeaders();
                Matcher mN = PATTERN_MULTIPART_PARAM_NAME.matcher(header);
                Matcher mF = PATTERN_MULTIPART_PARAM_FILENAME.matcher(header);
                Matcher mCT = PATTERN_MULTIPART_PARAM_CONTENT_TYPE.matcher(header);

                String name = mN.find() ? mN.group(1) : null;
                String filename = mF.find() ? mF.group(1) : null;
                String contentType = mCT.find() ? mCT.group(1) : "application/octet-stream";

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                multipartStream.readBodyData(output);

                if(filename != null) { //Multi-part file
                    multiPartParameters.add(new MultiPartParameter(name, output.toString(), contentType, filename));
                }
                else { //Multi-part regular parameter
                    paramsPost.put(name,output.toString());
                }
                nextPart = multipartStream.readBoundary();
            }
        } catch(MultipartStream.MalformedStreamException e) {
            //Oops
        } catch(IOException e) {
            //Oops
        }
    }
}
