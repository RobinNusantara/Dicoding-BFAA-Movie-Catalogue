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
    private TypedArray dataMoviePoster;
    private MoviesAdapter moviesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView recyclerViewMovies;
        setHasOptionsMenu(true);
        moviesAdapter = new MoviesAdapter(getActivity());
        recyclerViewMovies = view.findViewById(R.id.rv_movies_list);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMovies.setAdapter(moviesAdapter);

        prepare();
        addItem();

        return view;
    }

    private void prepare() {
        dataMovieTitle = getResources().getStringArray(R.array.item_movie_title);
        dataMoviePoster = getResources().obtainTypedArray(R.array.item_movie_poster);
        dataMovieRelease = getResources().getStringArray(R.array.item_movie_release);
        dataMovieScore = getResources().getStringArray(R.array.item_movie_score);
    }

    private void addItem() {
        ArrayList<Movies> listMovies = new ArrayList<>();
        for (int position = 0; position < dataMovieTitle.length; position++) {
            Movies dataMovies = new Movies();
            dataMovies.setMoviePoster(dataMoviePoster.getResourceId(position, -1));
            dataMovies.setMovieTitle(dataMovieTitle[position]);
            dataMovies.setMovieRelease(dataMovieRelease[position]);
            dataMovies.setMovieScore(dataMovieScore[position]);
            listMovies.add(dataMovies);
        }
        moviesAdapter.setMovies(listMovies);
    }

}