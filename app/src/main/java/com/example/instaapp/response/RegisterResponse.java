package com.example.instaapp.response;

public class RegisterResponse {
    String response;

    public RegisterResponse() {
        this.response = "";
    }
    public void setMessage(String response) {
        this.response = response;
    }
    public String getMessage() {
        return response;
    }
}
