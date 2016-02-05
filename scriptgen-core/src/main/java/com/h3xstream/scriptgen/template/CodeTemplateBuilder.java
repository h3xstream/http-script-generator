package com.h3xstream.scriptgen.template;

import com.h3xstream.scriptgen.model.DisplaySettings;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This utility class is a wrapper of the template engine chosen (FreeMarker).
 */
public class CodeTemplateBuilder {

    private List<HttpRequestInfo> requests;
    private String templatePath;
    private DisplaySettings displaySettings = new DisplaySettings();

    public CodeTemplateBuilder request(List<HttpRequestInfo> requests) {
        this.requests = requests;
        return this;
    }

    public CodeTemplateBuilder templatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }


    public CodeTemplateBuilder displaySettings(DisplaySettings displaySettings) {
        this.displaySettings = displaySettings;
        return this;
    }

    public String build() throws Exception {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/");
        Template tpl = cfg.getTemplate(templatePath);


        Map<String,Object> ctxData = new HashMap<String,Object>();
        if(displaySettings.isMinimalHeaders()) {
            ctxData.put("requests", HttpRequestUtil.withMinimalHeaders(requests));
        }
        else {
            ctxData.put("requests", requests);
        }
        ctxData.put("settings",displaySettings);
        ctxData.put("util", new TemplateUtil());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        tpl.process(ctxData, new OutputStreamWriter(out));
        out.flush();

        return new String(out.toByteArray());
    }

}
