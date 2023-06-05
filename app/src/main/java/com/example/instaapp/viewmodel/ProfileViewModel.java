package com.example.instaapp.viewmodel;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.model.Profile;
import com.example.instaapp.model.ProfileToEdit;
import com.example.instaapp.model.Token;
import com.example.instaapp.request.ProfileRequest;
import com.example.instaapp.response.PhotoResponse;
import com.example.instaapp.response.UserResponse;
import com.example.instaapp.service.RetrofitService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileToEdit> mutableLiveData;
    public ProfileViewModel(){this.mutableLiveData = new MutableLiveData<>();}

    public void setUpProfile(){
        mutableLiveData.setValue(new ProfileToEdit(Profile.getName(),Profile.getPhoto()));
    }

    public void saveProfilePhoto(RequestBody album, MultipartBody.Part body, FragmentActivity activity){

        Call<PhotoResponse> call = RetrofitService.getPhotoInterface().sendProfileImage("Bearer" + Token.getToken(),album,body);

        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }else{
                    Log.d("getPhoto",response.body().toString());
                    PhotoResponse p = response.body();
                    Profile.setPhoto(p.getPhoto());
                    activity.finish();
                    activity.startActivity(activity.getIntent());

                }
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.d("getPhoto",t.getMessage());
            }
        });
    }
    public void saveProfileName(String name, FragmentActivity activity){

        Call<UserResponse> call = RetrofitService.getUserInterface().changeUserName("Bearer" + Token.getToken(), new ProfileRequest(name,Profile.getEmail()));

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }else{
                    Log.d("change name ", response.body().getUser().getName());
                    Profile.setName(response.body().getUser().getName());
                    activity.finish();
                    activity.startActivity(activity.getIntent());

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("getPhoto",t.getMessage());
            }
        });
    }
    public void changeProfile(ProfileToEdit profile){
        mutableLiveData.setValue(profile);
    }

    public MutableLiveData<ProfileToEdit> getObservedData() {
        return mutableLiveData;
    }
}
