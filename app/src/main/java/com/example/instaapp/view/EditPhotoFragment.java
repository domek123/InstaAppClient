package com.example.instaapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaapp.R;
import com.example.instaapp.databinding.FragmentCameraBinding;
import com.example.instaapp.databinding.FragmentEditPhotoBinding;
import com.example.instaapp.model.Tag;
import com.example.instaapp.model.Token;
import com.example.instaapp.response.TagsResponse;
import com.example.instaapp.service.RetrofitService;
import com.example.instaapp.viewmodel.FilterViewModel;
import com.example.instaapp.viewmodel.TagsViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPhotoFragment extends Fragment {
    private FragmentEditPhotoBinding binding;
    FilterViewModel filterViewModel;
    TagsViewModel tagsViewModel;
    ArrayList<String> itemList = new ArrayList<>();
    String filter = "";
    String id = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditPhotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        filterViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);
        tagsViewModel = new ViewModelProvider(getActivity()).get(TagsViewModel.class);
        binding.setFilterViewModel(filterViewModel);
        binding.setTagsViewModel(tagsViewModel);
        getParentFragmentManager()
                .setFragmentResultListener("datafromfragment1", this, (s, b) -> {
                    id = b.getString("data");
                    Picasso.get().load(RetrofitService.BASE_URL + "/api/getfile/" + id).into(binding.editPhotoIV);
                    binding.setFilter.setOnClickListener(l->{

                        filter = binding.filterSpinner.getSelectedItem().toString();
                    });
                    binding.addTag.setOnClickListener(l->{
                        String tag = binding.tagSpinner.getSelectedItem().toString();
                        if(!itemList.contains(tag)){
                            itemList.add(tag);
                        }

                    });
                    binding.close.setOnClickListener(l->{
                        filterViewModel.setFilter(filter,id);
                        tagsViewModel.addTags(id,itemList);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flFragment, new HomeFragment())
                                .addToBackStack(null)
                                .commit();
                    });
                });

        return view;
    }
}