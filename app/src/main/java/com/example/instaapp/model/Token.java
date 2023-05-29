package com.example.instaapp.model;

public class Token {
    private  static String token;

    public Token(String token){
        Token.token = token;
    }
    public static String getToken(){
        return Token.token;
    }
}
