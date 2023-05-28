package com.example.instaapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.request.RegisterRequest;
import com.example.instaapp.response.RegisterResponse;
import com.example.instaapp.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterResponse> mutableUser;

    public RegisterViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }
    public void registerUser(String login, String email, String password) {
        Call<RegisterResponse> call = UserService.getUserInterface().postRegisterData(new RegisterRequest(login, email, password));

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(!response.isSuccessful()){
                    Log.d("xxx", String.valueOf(response.code()));
                    return;
                }else{
                    mutableUser.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("fail", "fail" + call + " " + t.getMessage());
            }
        });
    }

    public MutableLiveData<RegisterResponse> getObservedUser(){
        return mutableUser;
    }
}
