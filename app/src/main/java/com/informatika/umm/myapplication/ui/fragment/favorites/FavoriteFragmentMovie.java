package com.informatika.umm.myapplication.ui.fragment.favorites;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.FavoriteAdapterMovie;
import com.informatika.umm.myapplication.database.FavoriteHelper;
import com.informatika.umm.myapplication.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragmentMovie extends Fragment {

    private static final String KEY = "movies";
    private RecyclerView recyclerView;
    private FavoriteAdapterMovie adapter;
    private List<Movie> movieList = new ArrayList<>();
    private FavoriteHelper helper;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_favorite);
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY, adapter.getFavorite());
    }

    @Override
    public void onStart() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        helper = FavoriteHelper.getInstance(getContext());
        helper.open();

        adapter = new FavoriteAdapterMovie(getContext());
        recyclerView.setAdapter(adapter);

        if (bundle == null) {
            movieList.clear();
            movieList.addAll(helper.queryAllMovie());
            if (movieList != null) {
                adapter.setFavorite(movieList);
            } else {
                Toast.makeText(getContext(), "Empty Data", Toast.LENGTH_SHORT).show();
            }
        } else {
            List<Movie> list = bundle.getParcelableArrayList(KEY);
            if (list != null) {
                adapter.setFavorite(list);
            }
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
