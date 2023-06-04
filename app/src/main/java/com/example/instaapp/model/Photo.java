package com.example.instaapp.model;

import java.util.ArrayList;

public class Photo {
    private String id;
    private String albumName;
    private String originalName;
    private String url;
    private String lastChange;
    private ArrayList<HistoryChange> history;
    private ArrayList<Tag> tags;

    public Photo(String id, String albumName, String originalName, String url, String lastChange, ArrayList<HistoryChange> history, ArrayList<Tag> tags) {
        this.id = id;
        this.albumName = albumName;
        this.originalName = originalName;
        this.url = url;
        this.lastChange = lastChange;
        this.history = history;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getUrl() {
        return url;
    }

    public String getLastChange() {
        return lastChange;
    }

    public ArrayList<HistoryChange> getHistory() {
        return history;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }
}
class HistoryChange{
    private String status;
    private String lastModifiedDate;

    public HistoryChange(String status, String lastModifiedDate, String path) {
        this.status = status;
        this.lastModifiedDate = lastModifiedDate;
    }
}
