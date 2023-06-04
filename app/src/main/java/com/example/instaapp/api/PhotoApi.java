package com.example.instaapp.api;

import com.example.instaapp.model.Photo;
import com.example.instaapp.response.ImageResponse;
import com.example.instaapp.response.PhotoResponse;
import com.example.instaapp.response.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PhotoApi {
    @GET("/api/photos")

    Call<PhotoResponse> getAllPhotos(@Header("Authorization") String token);

    @GET("/api/photos/{albumName}")
    Call<PhotoResponse> getAlbumPhoto(@Header("Authorization") String token, @Path("albumName") String albumName);

    @GET("/api/getfile/{id}")
    Call<ResponseBody> getPhoto(@Header("Authorization") String token, @Path("id") String id);
}
