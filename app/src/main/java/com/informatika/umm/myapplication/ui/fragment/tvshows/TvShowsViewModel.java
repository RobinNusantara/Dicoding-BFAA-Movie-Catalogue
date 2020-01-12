package com.informatika.umm.myapplication.ui.fragment.tvshows;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.api.Client;
import com.informatika.umm.myapplication.api.Service;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/5/2019 12 2019
 * 13:57 Thu
 **/
public class TvShowsViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> listMovie = new MutableLiveData<>();

    void loadDiscoverMovie() {
        Service apiServie = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiServie.getDiscoverMovies("tv", BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                ArrayList<Movie> movies = null;
                if (response.body() != null) {
                    movies = response.body().getResults();
                }
                listMovie.postValue(movies);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "onFailure");
            }
        });
    }

    public void loadSimilarMovie(int id) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getSimilarMovies("tv",id, BuildConfig.API_KEY, BuildConfig.LANGUAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                ArrayList<Movie> movies = null;
                if (response.body() != null) {
                    movies = response.body().getResults();
                }
                listMovie.postValue(movies);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "onFailure");
            }
        });
    }

    public LiveData<List<Movie>> getMovie() {
        return listMovie;
    }
}
