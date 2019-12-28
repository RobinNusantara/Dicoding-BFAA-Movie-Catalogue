package com.informatika.umm.myapplication.api;

import com.informatika.umm.myapplication.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/28/2019 12 2019
 * 10:52 Sat
 **/
public interface Service {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey);
}
