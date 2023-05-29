package com.example.instaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instaapp.R;
import com.example.instaapp.databinding.ActivityRegisterBinding;
import com.example.instaapp.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.setRegistervm(registerViewModel);

        binding.singUpButton.setOnClickListener(l->{
            registerViewModel.registerUser(binding.loginET.getText().toString(),binding.emailET.getText().toString(),binding.passwordET.getText().toString(),RegisterActivity.this);
        });

        registerViewModel.getObservedUser().observe(RegisterActivity.this, s -> {
            binding.setRegistervm(registerViewModel);
        });
        startActivity(new Intent());
    }
}