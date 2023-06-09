package com.example.instaapp.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp.view.PhotoActivity;
import com.example.instaapp.R;
import com.example.instaapp.model.Photo;
import com.example.instaapp.model.Tag;
import com.example.instaapp.service.RetrofitService;
import com.example.instaapp.view.HomeFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Photo> list;
    Fragment fragment;
    public PostAdapter(List<Photo> list, Fragment fragment) {
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.postitem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        String url;
        Log.d("XXX",list.get(position).getOriginalName());
        String ext = list.get(position).getOriginalName().split("\\.")[1];
        if(ext.equals("jpg")){
            url = RetrofitService.BASE_URL + "/api/getfile/" + list.get(position).getId();
        }else{
            url = RetrofitService.BASE_URL + "/api/getvideo/" + list.get(position).getId();
        }
        Glide.with(holder.img.getContext())
                .load(url)
                .skipMemoryCache(true)
                .into(holder.img);
        Log.d("photo",list.get(position).toString());
        holder.button.setOnClickListener(l->{
            Intent intent = new Intent(fragment.getActivity(), PhotoActivity.class);
            intent.putExtra("url",url);
            intent.putExtra("ext",ext);
            intent.putExtra("loc",list.get(position).getRegion());
            fragment.startActivity(intent);
        });
        String desc = "";
        for(Tag tag : list.get(position).getTags()){
            desc += tag.getName();
        }
        holder.text.setText(desc);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView img;
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tags);
            img = itemView.findViewById(R.id.photo);
            button = itemView.findViewById(R.id.showPhotoBtn);
        }
    }
}
