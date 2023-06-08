package com.example.instaapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp.R;
import com.example.instaapp.databinding.FragmentEditPhotoBinding;
import com.example.instaapp.model.TagList;
import com.example.instaapp.service.RetrofitService;
import com.example.instaapp.viewmodel.FilterViewModel;
import com.example.instaapp.viewmodel.TagsViewModel;

import java.util.ArrayList;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.tagspinneritem,
                R.id.tagname,
                TagList.getTagList()
        );
        binding.tagSpinner.setAdapter(adapter);
        getParentFragmentManager()
                .setFragmentResultListener("datafromfragment1", this, (s, b) -> {
                    id = b.getString("data");
                    String ext = b.getString("ext");
                    String url = RetrofitService.BASE_URL + "/api/getfile/" + id;
                    if(ext.equals("mp4")){
                        url = RetrofitService.BASE_URL + "/api/getvideo/" + id;
                    }
                    Glide.with(binding.editPhotoIV.getContext())
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(binding.editPhotoIV);
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
                        getActivity().finish();
                        getActivity().startActivity(getActivity().getIntent());
                    });
                });
        binding.addLocBtn.setOnClickListener(l->{
            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.putExtra("id", id);

            startActivity(intent);
        });
        return view;
    }



}