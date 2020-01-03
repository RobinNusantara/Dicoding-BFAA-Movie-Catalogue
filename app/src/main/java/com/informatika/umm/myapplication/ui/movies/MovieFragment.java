package com.informatika.umm.myapplication.ui.movies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.MovieListAdapter;
import com.informatika.umm.myapplication.api.Client;
import com.informatika.umm.myapplication.api.Service;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.model.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private ShimmerFrameLayout shimmerFrameLayout;
    private MovieListAdapter listAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_container);
        shimmerFrameLayout.startShimmer();
        loadNowPlayingMovies();
    }

    private void loadNowPlayingMovies() {

        ArrayList<Movie> movies = new ArrayList<>();
        listAdapter = new MovieListAdapter(getContext(), movies);
        if (getActivity() != null) {
            RecyclerView rvNowPlayingMovies = getActivity().findViewById(R.id.rv_movies_now_playing);
            rvNowPlayingMovies.setHasFixedSize(true);
            LinearLayoutManager layoutNowPlayingMovies = new LinearLayoutManager(getContext());
            rvNowPlayingMovies.setLayoutManager(layoutNowPlayingMovies);
            rvNowPlayingMovies.setAdapter(listAdapter);
        }

        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getNowPlayingMovies(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                ArrayList<Movie> movies = null;
                if (response.body() != null) {
                    movies = response.body().getResults();
                }
                if (response.isSuccessful()) {
                    if (getActivity() != null) {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        listAdapter.setMovie(movies);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "Error");
                Toast.makeText(getContext(), "Error While Load Data Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}