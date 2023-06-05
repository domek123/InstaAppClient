package com.example.instaapp.request;

import com.example.instaapp.model.smallTag;

import java.util.ArrayList;

public class MassTagRequest {
    private String id;
    private ArrayList<smallTag> tags;

    public MassTagRequest(String id, ArrayList<smallTag> tags) {
        this.id = id;
        this.tags = tags;
    }
}
