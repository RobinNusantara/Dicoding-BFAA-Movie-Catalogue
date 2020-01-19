package com.informatika.umm.myapplication.ui.activity.detail.movies;

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
 * 16:50 Fri
 **/
public class DetailMovieViewModel extends ViewModel {

    private MutableLiveData<Movie> movies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> listMovie = new MutableLiveData<>();
    private MutableLiveData<List<Genre>> listGenre = new MutableLiveData<>();

    public void loadDetailMovie(Movie movie) {
        Service apiService = Client.getClient().create(Service.class);
        Call<Movie> call = apiService.getMovieDetails("movie", movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
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

    public void loadSimilarMovie(Movie movie) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getSimilarMovies("movie", movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
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

    public void loadGenreMovie(Movie movie) {
        Service apiService = Client.getClient().create(Service.class);
        Call<Movie> call = apiService.getMovieDetails("movie", movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
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

    LiveData<Movie> getDetailMovie() {
        return movies;
    }

    LiveData<List<Movie>> getSimilarMovie() {
        return listMovie;
    }

    LiveData<List<Genre>> getGenreMovie() {
        return listGenre;
    }
}
