package com.example.instaapp.model;

public class ProfileToEdit {
    private String name;
    private Photo photo;

    public ProfileToEdit(String name, Photo photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
