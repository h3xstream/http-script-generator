package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import org.testng.annotations.Test;

public class CodeTemplateBuilderPythonSulleyTest extends CodeTemplateBuilderBaseTest {


    HttpRequestInfo reqGet = HttpRequestInfoFixtures.getGetRequest();
    HttpRequestInfo reqPost = HttpRequestInfoFixtures.getPostRequest();

    @Test
    public void testGetTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/python_sulley.tpl","s_static(\"GET \")",reqGet);
    }

    @Test
    public void testPostTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/python_sulley.tpl","s_static(\"POST \")",reqPost);
    }
}
