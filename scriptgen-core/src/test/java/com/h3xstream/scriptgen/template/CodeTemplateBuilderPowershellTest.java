package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CodeTemplateBuilderPowershellTest extends CodeTemplateBuilderBaseTest {


    HttpRequestInfo reqGet = HttpRequestInfoFixtures.getGetRequest();
    HttpRequestInfo reqPost = HttpRequestInfoFixtures.getPostRequest();

    @Test
    public void testGetTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/psh_webrequest.tpl","-Body $paramsGet",reqGet);
    }

    @Test
    public void testPostTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/psh_webrequest.tpl","-Body $paramsPost",reqPost);
    }
}
