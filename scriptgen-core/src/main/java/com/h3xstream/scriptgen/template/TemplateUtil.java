package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.model.MultiPartParameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateUtil {

    /**
     * The following characters don't need to be URL encoded
     */
    private static List<Character> CHAR_NO_URL_ENCODE = Arrays.asList('_','-','*');

    /**
     * The following characters will be encoded by Python Requests. The character might not be decoded transparently by
     * the server.
     * Square bracket can be interpreted by the server as array type (test[]=1&test[]=2) or as key identifier (foo[bar]=123).
     * If special characters such as square bracket are found, it will fall back to raw body. (a single string parameter)
     * https://github.com/h3xstream/http-script-generator/issues/18
     */
    private static List<Character> CHAR_FALL_BACK_RAW_PY_REQUESTS = Arrays.asList('[',']');

    private String buildMap(Map<String,String> map,String start,String end,String keyValueSeparator, String entrySeparator) {
        StringBuilder buffer = new StringBuilder(start);

        int i=0;
        for(Map.Entry<String,String> e : map.entrySet()) {
            if(i++ != 0) buffer.append( entrySeparator );
            buffer.append("\""+ pythonStr(e.getKey()) + keyValueSeparator + pythonStr(e.getValue())+"\"");
        }

        buffer.append(end);
        return buffer.toString();
    }

    /**
     * This method set the basic escaping for all scripting language.
     * Each language will handle additional escaping if needed.
     * @param value
     * @return
     */
    private String genericString(String value) {
        StringBuilder buffer = new StringBuilder();
        for(int c=0;c<value.length();c++) {
            Character ch = value.charAt(c);

            if(ch == '\r') {
                buffer.append("\\r");
            }
            else if(ch == '\n') {
                buffer.append("\\n");
            }
            else if(ch == '"') {
                buffer.append("\\\"");
            }
            else if(ch == '\\') {
                buffer.append("\\\\");
            }
            else if(ch == '$' || ch == '#') {
                buffer.append("\\x"+String.format("%02x",Math.abs((int) ch)));
            }
            else if( 32 <= ch && ch <= 126) { //Digits, alpha and most specials
                buffer.append(ch);
            }
            else {
                buffer.append("\\x"+String.format("%02x",Math.abs((int) ch)));
            }
        }
        return buffer.toString();
    }


    ///Python util method

    public String pythonDict(Map<String,String> map) {
        return buildMap(map,"{","}","\":\"",",");
    }

    public String pythonStr(String value) {
        return genericString(value);
    }

    /**
     * Reproduce this code structure : http://docs.python-requests.org/en/latest/user/advanced/#post-multiple-multipart-encoded-files
     * @param parameters
     * @return
     */
    public String pythonDictMultipart(List<MultiPartParameter> parameters) {
        StringBuilder buffer = new StringBuilder();
        boolean first=true;
        for(MultiPartParameter p:parameters) {
            if(!first)
                buffer.append(",");

            buffer.append(String.format("('%s', ('%s', \"%s\", '%s'))", //
                    p.getName(), p.getFileName(), pythonStr(p.getValue()), p.getContentType()));

            first=false;
        }
        return "["+buffer.toString()+"]";
    }

    //Ruby

    public String rubyMap(Map<String,String> map) {
        return buildMap(map,"{","}","\"=>\"",",");
    }

    public String rubyStr(String value) {
        return genericString(value);
    }

    /**
     * Reproduce this code structure : https://github.com/nicksieger/multipart-post#synopsis
     * The file contents are not include.
     * @param parameters
     * @return
     */
    public String rubyDictMultipart(List<MultiPartParameter> parameters) {
        StringBuilder buffer = new StringBuilder();
        boolean first=true;
        for(MultiPartParameter p:parameters) {
            if(!first)
                buffer.append(",");

            buffer.append(String.format("\"%s\" => UploadIO.new(File.new(\"./%s\"), \"%s\", \"%s\")", //
                    p.getName(), p.getFileName(), p.getContentType(), p.getFileName()));

            first=false;
        }
        return buffer.toString();
    }
    //Perl

    public String perlMap(Map<String,String> map) {
        return buildMap(map,"{","}","\"=>\"",",");
    }

    public String perlStr(String value) {
        return genericString(value);
    }

    public String perlMergePostMultipart(Map<String,String> postParams, List<MultiPartParameter> parameters) {
        String postParamsPart =
                postParams == null ? "":
                buildMap(postParams,"","","\"=>\"",",") + ",";

        StringBuilder buffer = new StringBuilder(postParamsPart);
        boolean first=true;
        for(MultiPartParameter p:parameters) {
            if(!first)
                buffer.append(",");

            buffer.append(String.format("\"%s\"=>[\"%s\"]", //
                    p.getName(), p.getFileName()));

            first=false;
        }
        return "{"+buffer.toString()+"}";
    }

    //PHP

    public String phpStr(String value) {
        return genericString(value);
    }

    public String phpMap(Map<String,String> map) {
        return buildMap(map,"array(",")","\"=>\"",",");
    }

    public String phpHeadersList(Map<String,String> map) {

        return buildMap(map,"array(",")",": ",",");
    }


    public String phpCookies(Map<String,String> cookies) {
        StringBuilder str = new StringBuilder();
        int i=0;
        for(Map.Entry<String,String> e : cookies.entrySet()) {
            str.append((i++!=0?"; ":"")+phpStr(e.getKey())+"="+phpStr(e.getValue()));
        }
        return str.toString();
    }

    public String phpMergePostMultipart(Map<String,String> postParams, List<MultiPartParameter> parameters) {
        String postParamsPart = postParams == null ? "":
                buildMap(postParams,"","","\"=>\"",",") + ",";

        StringBuilder buffer = new StringBuilder(postParamsPart);
        if(parameters != null) {
            boolean first = true;
            for (MultiPartParameter p : parameters) {
                if (!first)
                    buffer.append(",");

                buffer.append(String.format("\"%s\"=>\"@./%s\"", //
                        p.getName(), p.getFileName()));

                first = false;
            }
        }
        return "array("+buffer.toString()+")";
    }

	// PowerShell
    public String powershellStr(String value) {
        return genericString(value);
    }

    public String powershellDict(Map<String,String> map) {
        return buildMap(map,"@{","}","\"=\"","; ");
    }

    //JavaScript


    public String jsStr(String value) {
        return genericString(value);
    }


    public String jsUrlParam(Map<String, String> map) {
        StringBuilder buffer = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String,String> param : map.entrySet()) {
            if(!first) {
                buffer.append("&");
            }
            buffer.append(jsUrlEncode(param.getKey()));
            buffer.append("=");
            buffer.append(jsUrlEncode(param.getValue()));

            first=false;
        }
        return buffer.toString();
    }


    public String jsUrlEncode(String value) {
        boolean containsSpecials = false;
        for(int i=0;i<value.length();i++) {
            if(!(Character.isAlphabetic(value.charAt(i)) || Character.isDigit(value.charAt(i)) || //
                    CHAR_NO_URL_ENCODE.contains(value.charAt(i)) )) {
                containsSpecials = true;
            }
        }
        return containsSpecials? "\"+escape(\""+jsStr(value)+"\")+\"":jsStr(value);
    }

    public String jsMap(Map<String,String> map) {
        return buildMap(map,"{","}","\":\"",",");
    }

    //XML

    public String xmlStr(String value) {
        return XmlUtil.escapeValue(value);
    }

    //Special filter

    public String alphaOnly(String value) {
        if(value.matches("^[a-zA-Z]+$"))
            return value;
        else
            return "GET"; //Safe fall back (Should not happen)
    }

    public boolean hasSpecialCharsPyRequest(Map<String,String> map) {
        if(map == null) return false;

        for(Map.Entry<String,String> e : map.entrySet()) {
            for(Character ch : CHAR_FALL_BACK_RAW_PY_REQUESTS) {
                if (e.getKey().indexOf(ch) != -1) {
                    return true; //Special character found
                }
            }
        }
        return false;
    }

    //Detect conditions on list of requests

    public boolean atLeastOneSsl(List<HttpRequestInfo> requests) {
        for(HttpRequestInfo req : requests) {
            if(req.isSsl()) {
                return true;
            }
        }
        return false;
    }

    public boolean atLeastOneCookie(List<HttpRequestInfo> requests) {
        for(HttpRequestInfo req : requests) {
            if(req.getCookies() != null && req.getCookies().size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean atLeastOneBasicAuth(List<HttpRequestInfo> requests) {
        for(HttpRequestInfo req : requests) {
            if(req.getBasicAuth() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean atLeastOneMultipart(List<HttpRequestInfo> requests) {
        for(HttpRequestInfo req : requests) {
            if(req.getParametersMultipart() != null) {
                return true;
            }
        }
        return false;
    }
}
