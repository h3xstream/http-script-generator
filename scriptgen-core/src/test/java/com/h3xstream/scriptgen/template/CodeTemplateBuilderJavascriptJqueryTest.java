package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import org.testng.annotations.Test;

public class CodeTemplateBuilderJavascriptJqueryTest extends CodeTemplateBuilderBaseTest {


    HttpRequestInfo reqGet = HttpRequestInfoFixtures.getGetRequest();
    HttpRequestInfo reqPost = HttpRequestInfoFixtures.getPostRequest();

    @Test
    public void testGetTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/javascript_jquery.tpl","type: \"get",reqGet);
    }

    @Test
    public void testPostTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/javascript_jquery.tpl","type: \"post",reqPost);
    }
}
