package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CodeTemplateBuilderTest {

    @Test
    public void basicTemplate() throws Exception {
        HttpRequestInfo req1 = spy(HttpRequestInfoFixtures.getPostRequest());

        String testUrl = "http://blog.h3xstream.com/secret.txt";
        when(req1.getUrl()).thenReturn(testUrl);
        String output = new CodeTemplateBuilder().request(Arrays.asList(req1)).templatePath("templates/basic_template.tpl").build();

        assertTrue(output.contains(testUrl),"The url bind to the model was not include in the template.");
    }
}
