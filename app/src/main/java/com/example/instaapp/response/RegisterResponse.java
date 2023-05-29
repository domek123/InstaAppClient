package com.example.instaapp.response;

public class RegisterResponse {
    String message;

    public RegisterResponse() {
        this.message = "";
    }
    public RegisterResponse(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
