package com.example.instaapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.model.Photo;
import com.example.instaapp.model.Profile;
import com.example.instaapp.model.ProfileToEdit;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileToEdit> mutableLiveData;

    public ProfileViewModel(){this.mutableLiveData = new MutableLiveData<>();}

    public void setUpProfile(){
        mutableLiveData.setValue(new ProfileToEdit(Profile.getName(),Profile.getPhoto()));
    }

    public void changeProfile(ProfileToEdit profile){
        mutableLiveData.setValue(profile);
    }

    public MutableLiveData<ProfileToEdit> getObservedData() {
        return mutableLiveData;
    }
}
