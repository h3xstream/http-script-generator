package com.h3xstream.scriptgen.template;

import java.util.Map;

public class TemplateUtil {



    private String buildMap(Map<String,String> map,String start,String end,String separator) {
        StringBuilder buffer = new StringBuilder("{");

        int i=0;
        for(Map.Entry<String,String> e : map.entrySet()) {
            if(i++ != 0) buffer.append(",");
            buffer.append("\""+ pythonStr(e.getKey()) + "\""+separator+"\"" + pythonStr(e.getValue())+"\"");
        }

        buffer.append("}");
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
        return buildMap(map,"{","}",":");
    }

    public String pythonStr(String value) {
        return genericString(value);
    }

    //Ruby

    public String rubyMap(Map<String,String> map) {
        return buildMap(map,"{","}","=>");
    }

    public String rubyStr(String value) {
        return genericString(value);
    }

    //Perl

    public String perlMap(Map<String,String> map) {
        return buildMap(map,"{","}","=>");
    }

    public String perlStr(String value) {
        return genericString(value);
    }
}
