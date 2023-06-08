package com.example.instaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.instaapp.databinding.ActivityPhotoBinding;

public class PhotoActivity extends AppCompatActivity {
    private ActivityPhotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExoPlayer player = new ExoPlayer.Builder(this).build();
        binding = ActivityPhotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        String ext = bundle.getString("ext");
        String loc = bundle.getString("loc");
        Log.d("video",url + " " +  ext);
        if(ext.equals("jpg")){
            binding.video.getLayoutParams().height = 0;
            Glide.with(binding.photo.getContext())
                    .load(url)
                    .skipMemoryCache(true)
                    .into(binding.photo);
        }else{
            binding.photo.getLayoutParams().height = 0;
            binding.video.setPlayer(player);
            MediaItem mediaItem = MediaItem.fromUri(url);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        }
        binding.showMap.setOnClickListener(l->{
            Intent intent = new Intent(this, MapViewActivity.class);
            intent.putExtra("loc",loc);
            startActivity(intent);
        });
    }
}