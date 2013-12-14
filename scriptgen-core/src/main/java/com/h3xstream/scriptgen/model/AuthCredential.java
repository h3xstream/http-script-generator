package com.h3xstream.scriptgen.model;

public class AuthCredential {

    private final String username;
    private final String password;

    public AuthCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
