package com.example.instaapp.viewmodel;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.model.Token;
import com.example.instaapp.request.LoginRequest;
import com.example.instaapp.request.RegisterRequest;
import com.example.instaapp.response.RegisterResponse;
import com.example.instaapp.service.RetrofitService;
import com.example.instaapp.view.LoginActivity;
import com.example.instaapp.view.MainActivity;
import com.example.instaapp.view.RegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterResponse> mutableUser;

    public RegisterViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }
    public void registerUser(String login, String email, String password) {
        Call<RegisterResponse> call = RetrofitService.getUserInterface().postRegisterData(new RegisterRequest(login, email, password));

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(!response.isSuccessful()){
                    Log.d("xxx", String.valueOf(response.code()) + response.body());
                    RegisterResponse registerResponse = new RegisterResponse();
                    if(response.code() == 409){
                        registerResponse.setMessage("użytkownik o podanym mailu istnieje");
                    }else if(response.code() == 400){
                        registerResponse.setMessage("niepoprawne dane");
                    }
                    mutableUser.setValue(registerResponse);
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
