package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CodeTemplateBuilderPythonTest {

    @Test
    public void testGetTemplate() throws Exception {
        String tpl = "com/h3xstream/scriptgen/templates/python_requests.tpl";
        HttpRequestInfo req1 = HttpRequestInfoFixtures.getGetRequest();

        String output = new CodeTemplateBuilder().request(req1).templatePath(tpl).build();

        System.out.println("=====");
        System.out.println(output);
        System.out.println("=====");

        assertTrue(output.contains("session.get("),"The method get from session class doesn't appears to be call.");
    }

    @Test
    public void testPostTemplate() throws Exception {
        String tpl = "com/h3xstream/scriptgen/templates/python_requests.tpl";
        HttpRequestInfo req1 = HttpRequestInfoFixtures.getPostRequest();

        String output = new CodeTemplateBuilder().request(req1).templatePath(tpl).build();

        System.out.println("=====");
        System.out.println(output);
        System.out.println("=====");

        assertTrue(output.contains("session.post("),"The method post from session class doesn't appears to be call.");
    }
}
