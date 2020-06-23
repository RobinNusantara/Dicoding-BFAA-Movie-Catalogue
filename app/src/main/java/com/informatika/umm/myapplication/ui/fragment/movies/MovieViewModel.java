package com.informatika.umm.myapplication.ui.fragment.movies;

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
 * created by : Robin Nusantara on 1/9/2020 01 2020
 * 16:15 Thu
 **/
public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> listMovie = new MutableLiveData<>();

    void loadDiscoverMovie() {
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getDiscoverMovies("movie", BuildConfig.API_KEY);
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

    LiveData<List<Movie>> getMovie() {
        return listMovie;
    }
}