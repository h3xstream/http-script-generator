package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import org.testng.annotations.Test;

public class CodeTemplateBuilderPythonTest {

    @Test
    public void testGetTemplate() throws Exception {
        String tpl = "com/h3xstream/scriptgen/templates/python_requets.htm";
        HttpRequestInfo req1 = HttpRequestInfoFixtures.getPostRequest();

        String output = new CodeTemplateBuilder().request(req1).templatePath(tpl).build();

        System.out.println("=====");
        System.out.println(output);
        System.out.println("=====");
    }

    @Test
    public void testPostTemplate() {

    }
}
