package com.h3xstream.scriptgen;

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

    public String build() {
        return "";
    }
}
