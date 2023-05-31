package com.example.instaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.instaapp.databinding.ActivityLoginBinding;
import com.example.instaapp.model.Token;
import com.example.instaapp.request.LoginRequest;
import com.example.instaapp.response.RegisterResponse;
import com.example.instaapp.service.RetrofitService;
import com.example.instaapp.viewmodel.RegisterViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.singInButton.setOnClickListener(l->{
            Call<RegisterResponse> call = RetrofitService.getUserInterface().postLoginData(new LoginRequest(binding.emailET.getText().toString(),binding.passwordET.getText().toString()));

            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if(!response.isSuccessful()){
                        Log.d("xxx", String.valueOf(response.code()));
                        return;
                    }else{
                        RegisterResponse registerResponse = response.body();
                        Token.setToken(registerResponse.getMessage());
                        Log.d("token",Token.getToken());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Log.d("fail", "fail" + call + " " + t.getMessage());
                }
            });
        });

        binding.singUpButton.setOnClickListener(l->{
            finish();
        });
    }
}