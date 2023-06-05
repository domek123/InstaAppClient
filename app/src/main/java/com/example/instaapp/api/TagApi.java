package com.example.instaapp.api;

import com.example.instaapp.request.FilterRequest;
import com.example.instaapp.request.LoginRequest;
import com.example.instaapp.request.RegisterRequest;
import com.example.instaapp.response.PhotosResponse;
import com.example.instaapp.response.RegisterResponse;
import com.example.instaapp.response.TagsResponse;
import com.example.instaapp.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TagApi {
    @GET("/api/tags")
    Call<TagsResponse> getTags(@Header("Authorization") String token);

    @PATCH("/api/filters")
    Call<PhotosResponse> setFilter(@Header("Authorization") String token, @Body FilterRequest filterRequest);
}

