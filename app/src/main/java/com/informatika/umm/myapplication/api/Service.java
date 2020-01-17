package com.informatika.umm.myapplication.api;

import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/28/2019 12 2019
 * 10:52 Sat
 **/
public interface Service {

    @GET("discover/{type}")
    Call<MovieResponse> getDiscoverMovies(
            @Path("type") String type,
            @Query("api_key") String apiKey);

    @GET("{type}/{movie_id}/similar")
    Call<MovieResponse> getSimilarMovies(
            @Path("type") String type,
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("{type}/{movie_id}")
    Call<Movie> getMovieDetails(
            @Path("type") String type,
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language);
}
