package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import org.testng.annotations.Test;

public class CodeTemplateBuilderPerlTest extends CodeTemplateBuilderBaseTest {
    HttpRequestInfo reqGet = HttpRequestInfoFixtures.getGetRequest();
    HttpRequestInfo reqPost = HttpRequestInfoFixtures.getPostRequest();

    @Test
     public void testGetTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/perl_lwp.tpl","my $req = GET",reqGet);
    }

    @Test
    public void testPostTemplate() throws Exception {
        testTemplateContains("com/h3xstream/scriptgen/templates/perl_lwp.tpl","my $req = POST",reqPost);
    }
}
