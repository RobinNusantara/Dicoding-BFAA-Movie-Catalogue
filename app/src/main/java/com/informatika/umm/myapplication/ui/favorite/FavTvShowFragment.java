package com.informatika.umm.myapplication.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.informatika.umm.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvShowFragment extends Fragment {


    public FavTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tv_show, container, false);
    }
}
