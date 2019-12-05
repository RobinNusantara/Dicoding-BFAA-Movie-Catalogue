package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.Objects;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/30/2019 11 2019
 * 10:22 Sat
 **/
public class Movies implements Parcelable {

    private String moviePoster;
    private String movieBackdrop;
    private String movieTitle;
    private Double movieScore;
    private String movieRelease;
    private String movieOverview;
    private String movieRuntime;

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Double getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(Double movieScore) {
        this.movieScore = movieScore;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(String movieRelease) {
        this.movieRelease = movieRelease;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }

    public String getMovieRuntime() {
        return movieRuntime;
    }

    public void setMovieRuntime(String movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public Movies() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.moviePoster);
        dest.writeString(this.movieBackdrop);
        dest.writeString(this.movieTitle);
        dest.writeDouble(this.movieScore);
        dest.writeString(this.movieRelease);
        dest.writeString(this.movieOverview);
    }

    protected Movies(Parcel in) {
        this.moviePoster = in.readString();
        this.movieBackdrop = in.readString();
        this.movieTitle = in.readString();
        this.movieScore = in.readDouble();
        this.movieRelease = in.readString();
        this.movieOverview = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public Movies(JSONObject object) {
        try {
            String moviePoster = object.getString("poster_path");
            String movieBackdrop = object.getString("backdrop_path");
            String movieTitle = object.getString("title");
            Double movieScore = object.getDouble("vote_average");
            String movieRelease = object.getString("release_date");
            String movieOverview = object.getString("overview");

            this.moviePoster = moviePoster;
            this.movieBackdrop = movieBackdrop;
            this.movieTitle = movieTitle;
            this.movieScore = movieScore;
            this.movieRelease = movieRelease;
            this.movieOverview = movieOverview;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
