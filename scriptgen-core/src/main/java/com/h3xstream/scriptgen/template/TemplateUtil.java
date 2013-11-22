package com.h3xstream.scriptgen.template;

import java.util.Map;

public class TemplateUtil {

    public String pythonDict(Map<String,String> map) {
        StringBuilder buffer = new StringBuilder("{");

        int i=0;
        for(Map.Entry<String,String> e : map.entrySet()) {
            if(i++ != 0) buffer.append(",");
            buffer.append("\""+ pythonStr(e.getKey()) + "\":\"" + pythonStr(e.getValue())+"\"");
        }

        buffer.append("}");
        return buffer.toString();
    }

    public String pythonStr(String value) {
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
                buffer.append("\\u"+String.format("%04x",Math.abs((int) ch)));
            }



        }
        return buffer.toString();
    }

}
