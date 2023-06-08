package com.example.instaapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.instaapp.adapter.PostAdapter;
import com.example.instaapp.databinding.FragmentHomeBinding;
import com.example.instaapp.model.Photo;
import com.example.instaapp.model.Profile;
import com.example.instaapp.model.Token;
import com.example.instaapp.response.PhotosResponse;
import com.example.instaapp.service.RetrofitService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    ArrayList<Photo> photos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPhotos();
    }
    private void getPhotos(){
        Call<PhotosResponse> call = RetrofitService.getPhotoInterface().getAllPhotos("Bearer " + Token.getToken());
        call.enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("xxx", String.valueOf(response.code()));
                    return;
                }
                else
                {
                    PhotosResponse photosResponse = response.body();
                    Log.d("xxx", "onResponse: " + photosResponse.getPhotoList().size());
                    if(photosResponse.getPhotoList().size() != 0) {
                        photos = photosResponse.getPhotoList();
                        Collections.reverse(photos);
                        if(Profile.getPhoto() == null){
                            for(Photo p : photos){
                                Log.d("profileLogin",p.getAlbumName() + " " + Profile.getEmail());

                                String[] splitedUrl = p.getUrl().split("/");
                                Log.d("profileLoginPhoto",splitedUrl[splitedUrl.length-1]);
                                if (p.getAlbumName().equals(Profile.getEmail()) && splitedUrl[splitedUrl.length-1].equals("profile.jpg")) {
                                    Profile.setPhoto(p);

                                    Intent intent = new Intent(getActivity(),MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                        photos.remove(Profile.getPhoto());
                        StaggeredGridLayoutManager staggeredGridLayoutManager
                                = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
                        binding.albumPhotoRecView.setLayoutManager(staggeredGridLayoutManager);
                        PostAdapter adapter = new PostAdapter(photos,HomeFragment.this);
                        binding.albumPhotoRecView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<PhotosResponse> call, Throwable t) {
                Log.d("error getPhotos",t.getMessage());
            }
        });
    }
}