package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.HttpRequestInfo;
import com.h3xstream.scriptgen.LanguageOption;
import com.h3xstream.scriptgen.template.CodeTemplateBuilder;


public class GeneratorController {
    HttpRequestInfo request;

    public void updateHttpRequest(HttpRequestInfo request) {
        this.request = request;
    }


    public void updateLanguage(GeneratorFrame frame, LanguageOption newOption) throws Exception {
        System.out.println(newOption.getTitle());
        String codeGenerated = new CodeTemplateBuilder().request(request).templatePath(newOption.getTemplate()).build();

        frame.updateCode(codeGenerated,newOption.getSyntax());
    }
}
