package com.example.instaapp.request;

import com.example.instaapp.model.SmallTag;

import java.util.ArrayList;

public class MassTagRequest {
    private String id;
    private ArrayList<SmallTag> tags;

    public MassTagRequest(String id, ArrayList<SmallTag> tags) {
        this.id = id;
        this.tags = tags;
    }
}
