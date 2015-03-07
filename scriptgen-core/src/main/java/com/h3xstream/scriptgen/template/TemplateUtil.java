package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.model.MultiPartParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateUtil {

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
        //Map<String,String> map = new HashMap<String, String>();
        StringBuilder buffer = new StringBuilder();
        boolean first=true;
        for(MultiPartParameter p:parameters) {
            //map.put(p.getName(),p.getValue()); //Content-type is lost in the conversion (not usable with requests)
            if(!first)
                buffer.append(",");

            buffer.append(String.format("('%s', ('%s', \"%s\", '%s'))", //
                    p.getName(), p.getFileName(), pythonStr(p.getValue()), p.getContentType()));

            first=false;
        }
        return "["+buffer+"]";
        //return pythonDict(map);
    }

    //Ruby

    public String rubyMap(Map<String,String> map) {
        return buildMap(map,"{","}","\"=>\"",",");
    }

    public String rubyStr(String value) {
        return genericString(value);
    }

    //Perl

    public String perlMap(Map<String,String> map) {
        return buildMap(map,"{","}","\"=>\"",",");
    }

    public String perlStr(String value) {
        return genericString(value);
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

    public String phpUrlEncode(String value) {
        boolean containsSpecials = false;
        for(int i=0;i<value.length();i++) {
            if(!(Character.isAlphabetic(value.charAt(i)) || Character.isDigit(value.charAt(i)))) {
                containsSpecials = true;
            }
        }
        return containsSpecials? "urlencode(\""+phpStr(value)+"\")":"\""+phpStr(value)+"\"";
    }

    public String phpCookies(Map<String,String> cookies) {
        StringBuilder str = new StringBuilder();
        int i=0;
        for(Map.Entry<String,String> e : cookies.entrySet()) {
            str.append((i++!=0?"; ":"")+e.getKey()+"="+e.getValue());
        }
        return str.toString();
    }

	// PowerShell
    public String powershellStr(String value) {
        return genericString(value);
    }

    public String powershellDict(Map<String,String> map) {
        return buildMap(map,"@{","}","\"=\"","; ");
    }

}
