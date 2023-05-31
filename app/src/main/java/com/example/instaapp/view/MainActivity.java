package com.example.instaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.instaapp.R;
import com.example.instaapp.databinding.ActivityMainBinding;
import com.example.instaapp.model.Token;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        Log.d("token", Token.getToken());
        Fragment homeFr = new HomeFragment();
        Fragment searchFr = new SearchFragment();
        Fragment profileFr = new ProfileFragment();
        replaceFragment(homeFr);
        activityMainBinding.bottomNavigationView.setOnItemSelectedListener(item->{
            int id = item.getItemId();

            switch(id){
                case R.id.home:
                    replaceFragment(homeFr);
                    break;
                case R.id.search:
                    replaceFragment(searchFr);
                    break;
                case R.id.profile:
                    replaceFragment(profileFr);
                    break;
            }

            return true;
        });


    }

    public void replaceFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }
}