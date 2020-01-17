package com.informatika.umm.myapplication.view.activity.detail.tvshows;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.api.Client;
import com.informatika.umm.myapplication.api.Service;
import com.informatika.umm.myapplication.model.Genre;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/17/2020 01 2020
 * 19:10 Fri
 **/
public class DetailTvShowViewModel extends ViewModel {

    private MutableLiveData<Movie> movies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> listMovie = new MutableLiveData<>();
    private MutableLiveData<List<Genre>> listGenre = new MutableLiveData<>();

    public void loadDetailTvShow(Movie movie) {
        Service apiService = Client.getClient().create(Service.class);
        Call<Movie> call = apiService.getMovieDetails("tv", movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.body() != null) {
                    movies.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "onFailure");
            }
        });
    }

    public void loadSimilarTvShow(Movie movie) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getSimilarMovies("tv", movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                List<Movie> movies = null;
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

    public void loadGenreTvShow(Movie movie) {
        Service apiService = Client.getClient().create(Service.class);
        Call<Movie> call = apiService.getMovieDetails("tv", movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                List<Genre> genres = null;
                if (response.body() != null) {
                    genres = response.body().getMovieGenre();
                }
                listGenre.postValue(genres);
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "onFailure");
            }
        });
    }

    LiveData<Movie> getDetailTvShow() {
        return movies;
    }

    LiveData<List<Movie>> getSimilarTvShow() {
        return listMovie;
    }

    LiveData<List<Genre>> getGenreTvShow() {
        return listGenre;
    }
}
