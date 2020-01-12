package com.informatika.umm.myapplication.ui.fragment.tvshows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.MovieListAdapter;
import com.informatika.umm.myapplication.adapter.TvShowsAdapter;
import com.informatika.umm.myapplication.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class TvShowsFragment extends Fragment {

    private ShimmerFrameLayout shimmerFrameLayout;
    private TvShowsAdapter listAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvshows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_container);
        shimmerFrameLayout.startShimmer();

        List<Movie> movieList = new ArrayList<>();
        listAdapter = new TvShowsAdapter(getContext(), movieList);
        if (getActivity() != null) {
            RecyclerView rvNowPlayingMovies = view.findViewById(R.id.rv_movies_now_playing);
            rvNowPlayingMovies.setHasFixedSize(true);
            LinearLayoutManager layoutNowPlayingMovies = new LinearLayoutManager(getContext());
            rvNowPlayingMovies.setLayoutManager(layoutNowPlayingMovies);
            rvNowPlayingMovies.setAdapter(listAdapter);
        }

        TvShowsViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);
        viewModel.loadDiscoverMovie();
        viewModel.getMovie().observe(getViewLifecycleOwner(), getMovie);
    }

    private Observer<List<Movie>> getMovie = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movie) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            listAdapter.setMovie(movie);
        }
    };
}