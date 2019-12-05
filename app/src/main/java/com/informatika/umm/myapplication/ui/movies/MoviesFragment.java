package com.informatika.umm.myapplication.ui.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.MoviesAdapter;
import com.informatika.umm.myapplication.model.Movies;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {


    private MoviesAdapter moviesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView recyclerViewMovies;
        moviesAdapter = new MoviesAdapter(getActivity());
        recyclerViewMovies = view.findViewById(R.id.rv_movies_list);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMovies.setAdapter(moviesAdapter);

        MoviesViewModel moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        moviesViewModel.getMovies().observe(getViewLifecycleOwner(), getMovies);
        moviesViewModel.setMovies();

        return view;
    }

    private Observer<ArrayList<Movies>> getMovies = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            moviesAdapter.setMovies(movies);
        }
    };

}