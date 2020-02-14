package com.informatika.umm.consumerapp.model;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/13/2020 02 2020
 * 17:26 Thu
 **/
public class Movie {

    private String movieTitle;
    private String moviePoster;
    private String movieRelease;
    private Double movieScore;
    private String movieOverview;
    private String movieType;

    public Movie(String movieTitle, String moviePoster, String movieRelease, Double movieScore, String movieOverview,String movieType) {
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieRelease = movieRelease;
        this.movieScore = movieScore;
        this.movieOverview = movieOverview;
        this.movieType = movieType;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public Double getMovieScore() {
        return movieScore;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieType() {
        return movieType;
    }
}
