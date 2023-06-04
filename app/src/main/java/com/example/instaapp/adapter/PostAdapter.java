package com.example.instaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaapp.R;
import com.example.instaapp.model.Photo;
import com.example.instaapp.model.Profile;
import com.example.instaapp.model.Tag;
import com.example.instaapp.view.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Photo> list;
    HomeFragment homeFragment;
    public PostAdapter(List<Photo> list, HomeFragment homeFragment) {
        this.list = list;
        this.homeFragment = homeFragment;
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
        Picasso.get().load("http://192.168.0.176:3000/api/getfile/" + list.get(position).getId()).into(holder.img);
        String desc = "#XDDD";
        for(Tag tag : list.get(position).getTags()){
            desc += tag.getName();
        }
        holder.text.setText(desc);
        holder.author.setText(Profile.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView text;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.authorTV);
            text = itemView.findViewById(R.id.tags);
            img = itemView.findViewById(R.id.photo);
        }
    }
}
