package com.example.instaapp.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.instaapp.databinding.FragmentHomeBinding;
import com.example.instaapp.model.Profile;
import com.example.instaapp.model.Token;
import com.example.instaapp.response.ImageResponse;
import com.example.instaapp.response.PhotoResponse;
import com.example.instaapp.service.RetrofitService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("XXX",Profile.getEmail());
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
                        String id = photoResponse.getPhotoList().get(0).getId();
                        Call<ResponseBody> imageCall = RetrofitService.getPhotoInterface().getPhoto("Bearer " + Token.getToken(),id);
                        imageCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                InputStream b = response.body().byteStream();
                                BufferedInputStream buf = new BufferedInputStream(b);
                                Bitmap bmp = BitmapFactory.decodeStream(buf);
                                binding.img.setImageBitmap(bmp);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.d("error getPhotos",t.getMessage());
                            }
                        });
                    }


                }
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.d("error getPhotos",t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }
}