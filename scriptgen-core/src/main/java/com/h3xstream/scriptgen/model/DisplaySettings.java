package com.h3xstream.scriptgen.model;

public class DisplaySettings {
    private boolean proxy = false;
    private boolean minimalHeaders = false;
    private boolean disableSsl = false;


    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    public boolean isMinimalHeaders() {
        return minimalHeaders;
    }

    public void setMinimalHeaders(boolean minimalHeaders) {
        this.minimalHeaders = minimalHeaders;
    }

    public boolean isDisableSsl() {
        return disableSsl;
    }

    public void setDisableSsl(boolean disableSsl) {
        this.disableSsl = disableSsl;
    }

    @Override
    public String toString() {
        return "[proxy=" + proxy + ",minimalHeaders=" + minimalHeaders + ",disableSsl=" + disableSsl + "]";
    }
}
