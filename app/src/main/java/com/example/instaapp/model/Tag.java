package com.example.instaapp.model;

public class Tag{
    private String id;
    private String name;
    private int popularity;

    public Tag(String id, String name, int popularity) {
        this.id = id;
        this.name = name;
        this.popularity = popularity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }
}