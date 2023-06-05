package com.example.instaapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.model.Tag;
import com.example.instaapp.model.Token;
import com.example.instaapp.model.SmallTag;
import com.example.instaapp.request.MassTagRequest;
import com.example.instaapp.response.PhotoResponse;
import com.example.instaapp.response.TagsResponse;
import com.example.instaapp.service.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> mutableItemList;

    private ArrayList<String> itemList;

    public TagsViewModel() {
        this.mutableItemList = new MutableLiveData<>();
        this.itemList = new ArrayList<>();
        Call<TagsResponse> call = RetrofitService.getTagInterface().getTags("Bearer " + Token.getToken());

        call.enqueue(new Callback<TagsResponse>() {
            @Override
            public void onResponse(Call<TagsResponse> call, Response<TagsResponse> response) {
                if(!response.isSuccessful()){
                    Log.d("error getTags","XDD");
                }else{

                    for(Tag tag :response.body().getResponse()){
                        itemList.add(tag.getName());
                    }
                    mutableItemList.setValue(itemList);
                }
            }

            @Override
            public void onFailure(Call<TagsResponse> call, Throwable t) {
                Log.d("error gt",t.getMessage());
            }
        });
    }
    public void addTags(String id, ArrayList<String> tags){
        ArrayList<SmallTag> list = new ArrayList<>();
        for(String tag: tags){
            list.add(new SmallTag(tag));
        }
        Call<PhotoResponse> call = RetrofitService.getPhotoInterface().setTags("Bearer " + Token.getToken(), new MassTagRequest(id,list));

        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if(!response.isSuccessful()){
                    Log.d("xxx", String.valueOf(response.code()));
                    return;
                }else{
                    Log.d("XDDD", "udalo sie ");
                }
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.d("error SetTags", t.getMessage());
            }
        });

    }
    public MutableLiveData<ArrayList<String>> getObservedItemList() {

        return mutableItemList;
    }
}
