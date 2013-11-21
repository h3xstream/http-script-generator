package com.h3xstream.scriptgen;

import com.h3xstream.scriptgen.template.CodeTemplateBuilder;
import org.testng.annotations.Test;

import java.io.IOException;

public class CodeTemplateBuilderTest {

    @Test
    public void basicTemplate() throws Exception {
        HttpRequestInfo req1 = HttpRequestInfoFixtures.getPostRequest();
        String output = new CodeTemplateBuilder().request(req1).templatePath("templates/basic.htm").build();
        System.out.println(output);
    }
}
