package com.example.instaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import com.example.instaapp.databinding.ActivityMainBinding;
import com.example.instaapp.viewmodel.RegisterViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        activityMainBinding.setRegisterViewModel(registerViewModel);

        activityMainBinding.singUpButton.setOnClickListener(l->{
            registerViewModel.registerUser(activityMainBinding.loginET.getText().toString(),activityMainBinding.emailET.getText().toString(),activityMainBinding.passwordET.getText().toString());
        });

        registerViewModel.getObservedUser().observe(MainActivity.this, s -> {
            activityMainBinding.setRegisterViewModel(registerViewModel);
        });




    }
}