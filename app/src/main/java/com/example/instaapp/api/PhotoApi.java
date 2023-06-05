package com.example.instaapp.api;

import com.example.instaapp.model.Photo;
import com.example.instaapp.request.FilterRequest;
import com.example.instaapp.request.MassTagRequest;
import com.example.instaapp.response.PhotoResponse;
import com.example.instaapp.response.PhotosResponse;
import com.example.instaapp.response.TagsResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PhotoApi {
    @GET("/api/photos")

    Call<PhotosResponse> getAllPhotos(@Header("Authorization") String token);
    @GET("/api/photos/{albumName}")
    Call<PhotosResponse> getAlbumPhoto(@Header("Authorization") String token, @Path("albumName") String albumName);

    @GET("/api/getfile/{id}")
    Call<ResponseBody> getPhoto(@Header("Authorization") String token, @Path("id") String id);

    @Multipart
    @POST("/api/photos")
    Call<PhotoResponse> sendImage(
            @Header("Authorization") String token,
            @Part("album") RequestBody album,
            @Part MultipartBody.Part file
    );

    @PATCH("/api/photos/tags/mass")
    Call<PhotoResponse> setTags(@Header("Authorization") String token, @Body MassTagRequest massTagRequest);
}
