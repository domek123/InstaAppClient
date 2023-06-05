package com.example.instaapp.request;

public class FilterRequest {
    private String id;
    private String filter;
    private String options;

    public FilterRequest(String id, String filter, String options) {
        this.id = id;
        this.filter = filter;
        this.options = options;
    }
}
