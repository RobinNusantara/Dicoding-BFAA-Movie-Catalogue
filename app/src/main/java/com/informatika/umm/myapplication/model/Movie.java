package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/30/2019 11 2019
 * 10:22 Sat
 **/
public class Movie implements Parcelable {

    @SerializedName("id")
    private int movieId;

    @SerializedName("backdrop_path")
    private String movieBackdrop;

    @SerializedName("poster_path")
    private String moviePoster;

    @SerializedName(value = "title", alternate = "name")
    private String movieTitle;

    @SerializedName("vote_average")
    private Double movieScore;

    @SerializedName(value = "release_date", alternate = "first_air_date")
    private String movieRelease;

    @SerializedName("overview")
    private String movieOverview;

    @SerializedName("genres")
    private List<Genre> movieGenre;

    @SerializedName("runtime")
    private int movieRuntime;

    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime;

    @SerializedName("status")
    private String movieStatus;

    public int getMovieId() {
        return movieId;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Double getMovieScore() {
        return movieScore;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public List<Genre> getMovieGenre() {
        return movieGenre;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    public String getMovieStatus() {
        return movieStatus;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMovieScore(Double movieScore) {
        this.movieScore = movieScore;
    }

    public void setMovieRelease(String movieRelease) {
        this.movieRelease = movieRelease;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public void setMovieGenre(List<Genre> movieGenre) {
        this.movieGenre = movieGenre;
    }

    public void setMovieRuntime(int movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public void setMovieStatus(String movieStatus) {
        this.movieStatus = movieStatus;
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

    public Movie() {

    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieId);
        dest.writeString(this.movieBackdrop);
        dest.writeString(this.moviePoster);
        dest.writeString(this.movieTitle);
        dest.writeValue(this.movieScore);
        dest.writeString(this.movieRelease);
        dest.writeString(this.movieOverview);
        dest.writeList(this.movieGenre);
        dest.writeInt(this.movieRuntime);
        dest.writeList(this.episodeRunTime);
        dest.writeString(this.movieStatus);
    }

    protected Movie(Parcel in) {
        this.movieId = in.readInt();
        this.movieBackdrop = in.readString();
        this.moviePoster = in.readString();
        this.movieTitle = in.readString();
        this.movieScore = (Double) in.readValue(Double.class.getClassLoader());
        this.movieRelease = in.readString();
        this.movieOverview = in.readString();
        this.movieGenre = new ArrayList<Genre>();
        in.readList(this.movieGenre, Genre.class.getClassLoader());
        this.movieRuntime = in.readInt();
        this.episodeRunTime = new ArrayList<Integer>();
        in.readList(this.episodeRunTime, Integer.class.getClassLoader());
        this.movieStatus = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
