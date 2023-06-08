package com.example.instaapp.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaapp.databinding.FragmentCameraBinding;
import com.example.instaapp.model.Profile;
import com.example.instaapp.viewmodel.PhotosViewModel;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CameraFragment extends Fragment {
    FragmentCameraBinding binding;
    PhotosViewModel photoViewModel;
    private VideoCapture videoCapture;
    private CameraSelector cameraSelector;
    private ImageCapture imageCapture;
    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    private int PERMISSIONS_REQUEST_CODE = 100;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private boolean isRecording = false;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        photoViewModel = new ViewModelProvider(getActivity()).get(PhotosViewModel.class);
        if (!checkIfPermissionsGranted()) {
            requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        } else {
            cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (InterruptedException | ExecutionException e) {
                    // No errors need to be handled for this Future. This should never be reached.
                }
            }, ContextCompat.getMainExecutor(getActivity()));
        }

        binding.PhotoBtn.setOnClickListener(l -> {
            long second = System.currentTimeMillis();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, second);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(
                            getActivity().getContentResolver(),
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues)
                            .build();
            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(getActivity()),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {

                            String fileUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + second + ".jpg";

                            File file = new File(fileUri);
                            Log.d("zdjecie", "zrobiono" + fileUri + " " + file.getName());
                            RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
                            RequestBody album = RequestBody.create(MultipartBody.FORM, Profile.getEmail());
                            photoViewModel.savePhoto(album, body, getActivity());
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            Log.d("zdjecie", exception.getMessage());
                        }
                    });
        });
        binding.CameraBtn.setOnClickListener(l -> {
            if (isRecording == false) {
                try {
                    startRecord();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                isRecording = false;
                videoCapture.stopRecording();
            }

        });
        return view;
    }

    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    @SuppressLint("RestrictedApi")
    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(binding.previewView.getDisplay().getRotation())
                        .build();
        videoCapture =
                new VideoCapture.Builder()
                        .setTargetRotation(binding.previewView.getDisplay().getRotation())
                        .build();
        cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview);
    }



    @SuppressLint("RestrictedApi")

    private void startRecord() throws ExecutionException, InterruptedException {
        isRecording = true;
        long second = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, second);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        videoCapture.startRecording(
                new VideoCapture.OutputFileOptions.Builder(
                        getActivity().getContentResolver(),
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                ContextCompat.getMainExecutor(getActivity()),
                new VideoCapture.OnVideoSavedCallback() {
                    @Override
                    public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                        // video saved
                        String fileUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/" + second + ".mp4";

                        File file = new File(fileUri);
                        RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
                        RequestBody album = RequestBody.create(MultipartBody.FORM, Profile.getEmail());
                        photoViewModel.savePhoto(album, body, getActivity());
                    }

                    @Override
                    public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                        // error
                    }
                });
    }

}
