package com.example.instaapp.viewmodel;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.view.EditPhotoFragment;
import com.example.instaapp.R;
import com.example.instaapp.model.Photo;
import com.example.instaapp.model.Token;
import com.example.instaapp.response.PhotoResponse;
import com.example.instaapp.service.RetrofitService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosViewModel extends ViewModel {
    private Photo photo;
    private MutableLiveData<Photo> photos;
    public PhotosViewModel(){photos= new MutableLiveData<>();}

    public void savePhoto(RequestBody album, MultipartBody.Part body, FragmentActivity activity){
        Call<PhotoResponse> call = RetrofitService.getPhotoInterface().sendImage("Bearer" + Token.getToken(),album, body);

        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }else{
                    PhotoResponse p = response.body();
                    EditPhotoFragment editPhotoFragment = new EditPhotoFragment();
                    Bundle bundle = new Bundle();
                    Log.d("getPhoto",p.getPhoto().getOriginalName());
                    bundle.putString("data", "" + p.getPhoto().getId());
                    bundle.putString("ext", "" + p.getPhoto().getOriginalName().split("\\.")[1]);
                    activity.getSupportFragmentManager().setFragmentResult("datafromfragment1", bundle);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flFragment, editPhotoFragment)
                            .addToBackStack(null)
                            .commit();
                    photos.setValue(p.getPhoto());
                }
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.d("getPhoto",t.getMessage());
            }
        });
    }

    public Photo getPhoto() {
        return photo;
    }
}
