package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.model.HttpRequestInfo;

import java.util.Arrays;

import static org.testng.Assert.assertTrue;

public abstract class CodeTemplateBuilderBaseTest {

    public void testTemplateContains(String tpl, String shouldContains,HttpRequestInfo req) throws Exception {

        String output = new CodeTemplateBuilder().request(Arrays.asList(req)).templatePath(tpl).build();

        System.out.println("=====");
        System.out.println(output);
        System.out.println("=====");

        assertTrue(output.contains(shouldContains),"The template should contains the string : "+shouldContains);
    }
}
