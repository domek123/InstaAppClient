package com.example.instaapp.model;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean confirmed;
    private String token;

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
