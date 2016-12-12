package com.h3xstream.scriptgen.template;

public class XmlUtil {

    public static String escapeValue(String originalUnprotectedString) {
        if (originalUnprotectedString == null) {
            return null;
        }
        boolean anyCharactersProtected = false;

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < originalUnprotectedString.length(); i++) {
            char ch = originalUnprotectedString.charAt(i);

            boolean controlCharacter = ch < 32;
            boolean unicodeButNotAscii = ch > 126;
            boolean characterWithSpecialMeaningInXML = ch == '<' || ch == '&' || ch == '>' || ch == '"' || ch == '\'' || ch == '\\';

            if (characterWithSpecialMeaningInXML || unicodeButNotAscii || controlCharacter) {
                stringBuffer.append("&#" + (int) ch + ";");
                anyCharactersProtected = true;
            } else {
                stringBuffer.append(ch);
            }
        }
        if (anyCharactersProtected == false) {
            return originalUnprotectedString;
        }

        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(escapeValue("<b>test"));
        System.out.println(escapeValue("\"abc"));
        System.out.println(escapeValue("\\"));
    }

}
