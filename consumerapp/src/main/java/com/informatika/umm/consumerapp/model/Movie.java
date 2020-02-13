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

    public Movie(String movieTitle, String moviePoster, String movieRelease, Double movieScore) {
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieRelease = movieRelease;
        this.movieScore = movieScore;
    }

    public Float getRating() {
        float divideRating;
        if (movieScore != null) {
            divideRating = movieScore.floatValue();
            divideRating = divideRating / 2;
        } else {
            divideRating = 0;
        }
        return divideRating;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(String movieRelease) {
        this.movieRelease = movieRelease;
    }

    public Double getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(Double movieScore) {
        this.movieScore = movieScore;
    }
}
