package com.example.instaapp.model;

public class Profile {
    private static String name;
    private static String email;
    private static String password;
    private static Photo photo;

    public static void setData(String name,String email,String password){
        Profile.name = name;
        Profile.email = email;
        Profile.password = password;
    }

    public static String getName() {
        return name;
    }
    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setName(String name) {
        Profile.name = name;
    }

    public static void setEmail(String email) {
        Profile.email = email;
    }

    public static void setPassword(String password) {
        Profile.password = password;
    }
}
