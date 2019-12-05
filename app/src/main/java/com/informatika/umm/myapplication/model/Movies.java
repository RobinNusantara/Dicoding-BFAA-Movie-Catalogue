package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/30/2019 11 2019
 * 10:22 Sat
 **/
public class Movies implements Parcelable {

    private int moviePoster;
    private int movieBackdrop;
    private String movieTitle;
    private String movieScore;
    private String movieRelease;
    private String movieOverview;
    private String movieRuntime;

    public int getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(int moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(String movieScore) {
        this.movieScore = movieScore;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(String movieRelease) {
        this.movieRelease = movieRelease;
    }

    public int getMovieBackdrop() {
        return movieBackdrop;
    }

    public void setMovieBackdrop(int movieBackdrop) {
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
        dest.writeInt(this.moviePoster);
        dest.writeInt(this.movieBackdrop);
        dest.writeString(this.movieTitle);
        dest.writeString(this.movieScore);
        dest.writeString(this.movieRelease);
        dest.writeString(this.movieOverview);
        dest.writeString(this.movieRuntime);
    }

    protected Movies(Parcel in) {
        this.moviePoster = in.readInt();
        this.movieBackdrop = in.readInt();
        this.movieTitle = in.readString();
        this.movieScore = in.readString();
        this.movieRelease = in.readString();
        this.movieOverview = in.readString();
        this.movieRuntime = in.readString();
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
}
