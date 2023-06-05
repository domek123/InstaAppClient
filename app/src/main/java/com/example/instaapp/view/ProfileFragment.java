package com.example.instaapp.view;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaapp.R;
import com.example.instaapp.databinding.FragmentProfileBinding;
import com.example.instaapp.model.Profile;
import com.example.instaapp.service.RetrofitService;
import com.example.instaapp.viewmodel.ProfileViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    ProfileViewModel profileViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        profileViewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);

                binding.makePhotoBtn.setOnClickListener(l->{
            CameraFragment cameraFragment= new CameraFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, cameraFragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

        });
                if(Profile.getPhoto() != null){
                    String url = RetrofitService.BASE_URL + "/api/getfile/" + Profile.getPhoto().getId();
                    Picasso.get().load(url).into(binding.profilePictureIV);
                }else{
                    binding.profilePictureIV.setImageResource(R.drawable.user);
                }


        binding.profilePictureIV.setOnClickListener(l->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent,1000);
        });
                binding.changeName.setOnClickListener(l->{
                    profileViewModel.saveProfileName(binding.loginET.getText().toString(),getActivity());
                });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1000) {

            Uri selectedImage = data.getData();
            Bitmap bitmapImage = null;
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                File file = new File(String.valueOf(selectedImage));

                String name = file.getName().split("%")[file.getName().split("%").length-1];
                name = name.replace("2F","");
                String fileUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + name;
                file = new File(fileUri);
                RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
                RequestBody album = RequestBody.create(MultipartBody.FORM, Profile.getEmail());
                profileViewModel.saveProfilePhoto(album,body,getActivity());
                binding.profilePictureIV.setImageBitmap(bitmapImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}