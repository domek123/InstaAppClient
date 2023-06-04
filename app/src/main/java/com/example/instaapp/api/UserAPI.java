package com.example.instaapp.api;

import com.example.instaapp.request.LoginRequest;
import com.example.instaapp.request.RegisterRequest;
import com.example.instaapp.response.RegisterResponse;
import com.example.instaapp.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("/api/user/register")
    Call<RegisterResponse> postRegisterData(@Body RegisterRequest registerRequest);

    @POST("/api/user/login")
    Call<RegisterResponse> postLoginData(@Body LoginRequest LoginRequest);

    @GET("/api/profile")
    Call<UserResponse> getUser(@Header("Authorization") String token);
}
