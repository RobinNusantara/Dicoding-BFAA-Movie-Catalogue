package com.informatika.umm.myapplication.ui.movies;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.MoviesAdapter;
import com.informatika.umm.myapplication.model.Movies;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private String[] dataMovieTitle;
    private String[] dataMovieRelease;
    private String[] dataMovieScore;
    private String[] dataMovieOverview;
    private String[] dataMovieRuntime;
    private TypedArray dataMoviePoster;
    private TypedArray dataMovieBackdrop;
    private MoviesAdapter moviesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView recyclerViewMovies;
        moviesAdapter = new MoviesAdapter(getActivity());
        recyclerViewMovies = view.findViewById(R.id.rv_movies_list);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMovies.setAdapter(moviesAdapter);

        return view;
    }
}