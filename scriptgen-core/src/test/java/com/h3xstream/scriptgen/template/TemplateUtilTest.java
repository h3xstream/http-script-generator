package com.h3xstream.scriptgen.template;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TemplateUtilTest {

    private TemplateUtil util = new TemplateUtil();

    @Test
    public void pythonUtilMethods() {
        Map<String,String> p = new HashMap<String,String>();
        p.put("a","12345");
        p.put("b","\"test\"");
        p.put("uni","'%\u0001\u0002\u0003\u0000abc");
        String dictRepr = util.pythonDict(p);
        System.out.println("=======");
        System.out.println(dictRepr);
        System.out.println("=======");
    }
}
