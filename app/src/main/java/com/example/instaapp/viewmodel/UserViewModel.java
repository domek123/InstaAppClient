package com.example.instaapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.model.User;

public class UserViewModel extends ViewModel {
    public MutableLiveData<User> mutableLiveData;
    public UserViewModel(){
        mutableLiveData = new MutableLiveData<>();
    }
    public void setupData(User user){
        this.mutableLiveData.setValue(user);
    }
    public MutableLiveData<User> getObservedUser(){
        return this.mutableLiveData;
    }
    public void changeUser(User user){
        this.mutableLiveData.setValue(user);
    }
}
