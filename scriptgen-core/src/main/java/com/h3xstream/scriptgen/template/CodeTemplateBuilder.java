package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This utility class is a wrapper of the template engine chosen (FreeMarker).
 */
public class CodeTemplateBuilder {

    private HttpRequestInfo request;
    private String templatePath;

    public CodeTemplateBuilder request(HttpRequestInfo request) {
        this.request = request;
        return this;
    }

    public CodeTemplateBuilder templatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }

    public String build() throws Exception {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/");
        Template tpl = cfg.getTemplate(templatePath);

        Map<String,Object> ctxData = new HashMap<String,Object>();
        ctxData.put("req", request);
        ctxData.put("util", new TemplateUtil());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        tpl.process(ctxData, new OutputStreamWriter(out));
        out.flush();

        return new String(out.toByteArray());
    }
}
