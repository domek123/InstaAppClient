package com.example.instaapp.model;

public class Profile {
    private static String name;
    private static String email;
    private static String password;
    private static Photo photo = null;

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

    public static void setName(String name) {
        Profile.name = name;
    }

    public static void setPhoto(Photo photo) {
        Profile.photo = photo;
    }

    public static Photo getPhoto() {
        return photo;
    }

    public static void clearProfile(){
        Profile.name = "";
        Profile.email = "";
        Profile.photo = null;
    }
}
