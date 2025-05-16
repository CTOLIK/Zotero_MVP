package com.example.zoteromvp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zoteromvp.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        String[] aaaaa = new String[] {"Blyat","Ceka"};
////                        ListView lv_myLib = findViewById(R.id.listView);
//        ListView lv_myLib = (ListView)view.findViewById(R.id.MyLib);
////
////
//         //      ar_Items.toArray(aaaaa);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, aaaaa);
//
//
//        lv_myLib.setAdapter(adapter);


        return root;
    }

//    @Override
//    public void onCreate(){
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}