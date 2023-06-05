package com.example.instaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.instaapp.databinding.ActivityPhotoBinding;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {
    private ActivityPhotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        Picasso.get().load(url).into(binding.photo);
    }
}