package com.h3xstream.scriptgen.model;

public class MultiPartParameter {
    private final String name;
    private final String value;
    private final String contentType;
    private final String fileName;

    public MultiPartParameter(String name, String value, String contentType, String fileName) {
        this.name = name;
        this.value = value;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }
}
