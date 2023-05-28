package com.example.instaapp.api;

import com.example.instaapp.model.User;
import com.example.instaapp.request.RegisterRequest;
import com.example.instaapp.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("/api/user/register")
    Call<RegisterResponse> postRegisterData(@Body RegisterRequest registerRequest);
}
