package com.informatika.umm.myapplication.ui.activity.search.movies;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/21/2020 01 2020
 * 12:07 Tue
 **/
public class SearchMovieViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

    public void loadSearchMovie(String input) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getSearchMovies("movie", BuildConfig.API_KEY, BuildConfig.LANGUAGE, input);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    movieList.postValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "onFailure");
            }
        });
    }

    LiveData<List<Movie>> getSearchMovie() {
        return movieList;
    }
}
