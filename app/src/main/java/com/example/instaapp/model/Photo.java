package com.example.instaapp.model;

import java.util.ArrayList;

public class Photo {
    private String id;
    private String album;
    private String originalName;
    private String url;
    private String lastChange;
    private ArrayList<HistoryChange> history;
    private ArrayList<Tag> tags;
    private String location;
    public Photo(String id, String albumName, String originalName, String url, String lastChange, ArrayList<HistoryChange> history, ArrayList<Tag> tags,String location) {
        this.id = id;
        this.album = albumName;
        this.originalName = originalName;
        this.url = url;
        this.lastChange = lastChange;
        this.history = history;
        this.tags = tags;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getAlbumName() {
        return album;
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

    public String getRegion(){
        return this.location;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", album='" + album + '\'' +
                ", originalName='" + originalName + '\'' +
                ", url='" + url + '\'' +
                ", lastChange='" + lastChange + '\'' +
                ", history=" + history +
                ", tags=" + location +
                '}';
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

