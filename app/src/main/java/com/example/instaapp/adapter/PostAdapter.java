package com.example.instaapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaapp.view.PhotoActivity;
import com.example.instaapp.R;
import com.example.instaapp.model.Photo;
import com.example.instaapp.model.Tag;
import com.example.instaapp.service.RetrofitService;
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
        String url = RetrofitService.BASE_URL + "/api/getfile/" + list.get(position).getId();
        Picasso.get().load(url).into(holder.img);
        holder.button.setOnClickListener(l->{
            Intent intent = new Intent(homeFragment.getActivity(), PhotoActivity.class);
            intent.putExtra("url",url);
            homeFragment.startActivity(intent);
        });
        String desc = "";
        for(Tag tag : list.get(position).getTags()){
            desc += tag.getName();
        }
        holder.text.setText(desc);
        holder.author.setText(list.get(position).getAlbumName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView text;
        private ImageView img;
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.authorTV);
            text = itemView.findViewById(R.id.tags);
            img = itemView.findViewById(R.id.photo);
            button = itemView.findViewById(R.id.showPhotoBtn);
        }
    }
}
