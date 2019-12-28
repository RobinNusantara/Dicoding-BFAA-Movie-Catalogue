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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.MovieAdapterHorizontal;
import com.informatika.umm.myapplication.adapter.MovieAdapterVertical;
import com.informatika.umm.myapplication.api.Client;
import com.informatika.umm.myapplication.api.Service;
import com.informatika.umm.myapplication.model.MovieItem;
import com.informatika.umm.myapplication.model.MovieResponse;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private ShimmerFrameLayout shimmerFrameLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_container);
        shimmerFrameLayout.startShimmer();

        loadPopularMovies();
        loadNowPlayingMovies();
    }

    private void loadPopularMovies() {
        try {
            Service apiService = Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getPopularMovies(BuildConfig.API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    List<MovieItem> movies = null;
                    if (response.body() != null) {
                        movies = response.body().getResults();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MovieAdapterHorizontal adapterPopularMovies = new MovieAdapterHorizontal(getContext(), movies);
                            if (getActivity() != null) {
                                MultiSnapRecyclerView rvPopularMovies = getActivity().findViewById(R.id.rv_movies_popular);
                                LinearLayoutManager layoutPopularMovies = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                rvPopularMovies.setLayoutManager(layoutPopularMovies);
                                rvPopularMovies.setAdapter(adapterPopularMovies);
                                shimmerFrameLayout.setVisibility(View.GONE);
                                shimmerFrameLayout.stopShimmer();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Log.d(t.getMessage(), "Error");
                    Toast.makeText(getContext(), "Error while Load Data Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error");
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNowPlayingMovies() {
        try {
            Service apiService = Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getNowPlayingMovies(BuildConfig.API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    assert response.body() != null;
                    List<MovieItem> movies = response.body().getResults();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MovieAdapterVertical adapterNowPlayingMovies = new MovieAdapterVertical(getContext(), movies);
                            if (getActivity() != null) {
                                MultiSnapRecyclerView rvNowPlayingMovies = getActivity().findViewById(R.id.rv_movies_now_playing);
                                LinearLayoutManager layoutNowPlayingMovies = new LinearLayoutManager(getContext());
                                rvNowPlayingMovies.setLayoutManager(layoutNowPlayingMovies);
                                rvNowPlayingMovies.setAdapter(adapterNowPlayingMovies);
                                shimmerFrameLayout.setVisibility(View.GONE);
                                shimmerFrameLayout.stopShimmer();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Log.d(t.getMessage(), "Error");
                    Toast.makeText(getContext(), "Error while Load Data Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error");
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}