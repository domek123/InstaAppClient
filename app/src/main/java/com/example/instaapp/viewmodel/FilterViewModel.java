package com.example.instaapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.model.Profile;
import com.example.instaapp.model.Token;
import com.example.instaapp.model.User;
import com.example.instaapp.request.FilterRequest;
import com.example.instaapp.response.PhotosResponse;
import com.example.instaapp.response.UserResponse;
import com.example.instaapp.service.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> mutableItemList;

    private ArrayList<String> itemList;

    public FilterViewModel () {
        this.mutableItemList = new MutableLiveData<>();
        this.itemList = new ArrayList<>();
        itemList.add("flip");
        itemList.add("negate");
        this.mutableItemList.setValue(this.itemList);
    }
    public void setFilter(String filter,String id){
        Call<PhotosResponse> userResponseCall = RetrofitService.getTagInterface().setFilter("Bearer " + Token.getToken(),new FilterRequest(id,filter,""));
        userResponseCall.enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                if(!response.isSuccessful()){
                    Log.d("xxx", String.valueOf(response.code()));
                    return;
                }else{
                    Log.d("XDDD", "udalo sie ");
                }
            }

            @Override
            public void onFailure(Call<PhotosResponse> call, Throwable t) {
                Log.d("fail", "fail" + call + " " + t.getMessage());
            }
        });
    }
    public MutableLiveData<ArrayList<String>> getObservedItemList() {

        return mutableItemList;
    }
}
