package com.example.instaapp.service;

import com.example.instaapp.api.PhotoApi;
import com.example.instaapp.api.TagApi;
import com.example.instaapp.api.UserAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static final String BASE_URL = "http://192.168.0.176:3000";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // zwracamy interfejs na zewnątrz

    public static UserAPI getUserInterface() {return retrofit.create(UserAPI.class);}
    public static PhotoApi getPhotoInterface(){ return retrofit.create(PhotoApi.class);}
    public static TagApi getTagInterface(){ return retrofit.create(TagApi.class);}
}
