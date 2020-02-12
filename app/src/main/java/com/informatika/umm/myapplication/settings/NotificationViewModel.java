package com.informatika.umm.myapplication.settings;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/20/2020 01 2020
 * 00:39 Mon
 **/
public class NotificationViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> listMovie = new MutableLiveData<>();

    public void loadReleaseMovie() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String today = dateFormat.format(new Date());

        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getReleaseMovies(BuildConfig.API_KEY, today, today);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    for (Movie movie : response.body().getResults()) {
                        if (movie.getMovieRelease().equals(today)) {
                            listMovie.postValue(response.body().getResults());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "onFailure");
            }
        });
    }

    LiveData<List<Movie>> getReleaseMovie() {
        return listMovie;
    }
}
