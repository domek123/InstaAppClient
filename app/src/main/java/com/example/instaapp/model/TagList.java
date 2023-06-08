package com.example.instaapp.model;

import java.util.ArrayList;

public class TagList
{
    private static ArrayList<String> tagList;

    public static void setTagList(ArrayList<String> tagList) {
        TagList.tagList = tagList;
    }

    public static ArrayList<String> getTagList() {
        return tagList;
    }
}
