package com.h3xstream.scriptgen.template;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.model.HttpRequestInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {

    /**
     * The plugin is intended to generated exact representation of the request captured. Sometime, the code generated
     * is fill with optional headers that are probably added by the HTTP library. Removing the following header can
     * make a the code slightly cleaner.
     * Ref: http://en.wikipedia.org/wiki/List_of_HTTP_header_fields
     */
    private static final List<String> COMMON_HEADERS = Arrays.asList("accept-language", "accept-encoding",
            "cache-control", "connection", "if-match", "if-modified-since", "if-none-match", "if-range",
            "if-unmodified-since", "proxy-connection", "referer", "user-agent", "via");

    /**
     * Hide optional headers
     * @param requests
     * @return
     */
    public static List<HttpRequestInfo> withMinimalHeaders(List<HttpRequestInfo> requests) {
        List<HttpRequestInfo> transformRequests = new ArrayList<>();
        for(HttpRequestInfo req : requests) {
            Map<String,String> newHeaders = hideCommonHeaders(req.getHeaders());
            try {
                HttpRequestInfo cloneReq = req.clone();
                cloneReq.setHeaders(hideCommonHeaders(req.getHeaders()));
                transformRequests.add(cloneReq);
            } catch (CloneNotSupportedException e) {
                Log.error("Error during the clone operation : "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return transformRequests;
    }

    private static Map<String,String> hideCommonHeaders(Map<String,String> origHeaders) {
        Map<String,String> selectedHeaders = new HashMap<String,String>();
        for(Map.Entry<String,String> entry : origHeaders.entrySet()) {
            String headerName = entry.getKey().toLowerCase();

            if("accept".equals(headerName) && entry.getValue().contains("*")) {
                continue; //Remove Accept header unless targeting a specific content-type "application/json", "application/xml", ...
            }
            if(!COMMON_HEADERS.contains(headerName)) {
                selectedHeaders.put(entry.getKey(), entry.getValue());
            }

        }
        return selectedHeaders.size() == 0 ? null : selectedHeaders;
    }
}
