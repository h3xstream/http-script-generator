package com.h3xstream.scriptgen.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.h3xstream.scriptgen.HttpRequestInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
/*
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        MustacheFactory mf = new DefaultMustacheFactory();


        InputStream in = CodeTemplateBuilder.class.getResourceAsStream(templatePath);
        if(in == null) {
            throw new FileNotFoundException(templatePath);
        }

        Reader r = new InputStreamReader(in);
        Mustache mustache = mf.compile(r,"");
        Object[] scopes = {request,new TemplateUtil()};
        mustache.execute(new PrintWriter(out),scopes).flush();

        return new String(out.toByteArray(),"UTF-8");

        */

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
