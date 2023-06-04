package com.example.instaapp.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.instaapp.adapter.PostAdapter;
import com.example.instaapp.databinding.FragmentHomeBinding;
import com.example.instaapp.model.Photo;
import com.example.instaapp.model.Profile;
import com.example.instaapp.model.Token;
import com.example.instaapp.response.ImageResponse;
import com.example.instaapp.response.PhotoResponse;
import com.example.instaapp.service.RetrofitService;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.ResponseBody;
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
        Call<PhotoResponse> call = RetrofitService.getPhotoInterface().getAlbumPhoto("Bearer " + Token.getToken(), Profile.getEmail());
        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("xxx", String.valueOf(response.code()));
                    return;
                }
                else
                {
                    PhotoResponse photoResponse = response.body();
                    Log.d("xxx", "onResponse: " + photoResponse.getPhotoList().size());
                    if(photoResponse.getPhotoList().size() != 0) {
                        photos = photoResponse.getPhotoList();
                        StaggeredGridLayoutManager staggeredGridLayoutManager
                                = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
                        binding.albumPhotoRecView.setLayoutManager(staggeredGridLayoutManager);
                        PostAdapter adapter = new PostAdapter(photos,HomeFragment.this);
                        binding.albumPhotoRecView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.d("error getPhotos",t.getMessage());
            }
        });
    }
}