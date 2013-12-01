package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CodeTemplateBuilderRubyTest extends CodeTemplateBuilderBaseTest {


    HttpRequestInfo reqGet = HttpRequestInfoFixtures.getGetRequest();
    HttpRequestInfo reqPost = HttpRequestInfoFixtures.getPostRequest();

    @Test
    public void testGetTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/ruby_nethttp.tpl","Net::HTTP::Get",reqGet);
    }

    @Test
    public void testPostTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/ruby_nethttp.tpl","Net::HTTP::Post",reqPost);
    }
}
