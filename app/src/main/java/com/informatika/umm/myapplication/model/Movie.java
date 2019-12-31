package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("vote_average")
    private Double movieScore;

    @SerializedName("release_date")
    private String movieRelease;

    @SerializedName("overview")
    private String movieOverview;

    @SerializedName("genres")
    private ArrayList<Genre> movieGenre;

    @SerializedName("runtime")
    private int movieRuntime;

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

    public ArrayList<Genre> getMovieGenre() {
        return movieGenre;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    public Float getRating() {
        String movieRating = Double.toString(movieScore);
        float divideRating;
        if (!movieRating.isEmpty()) {
            divideRating = Float.parseFloat(movieRating);
            divideRating = divideRating / 2;
        } else {
            divideRating = 0;
        }
        return divideRating;
    }

    public Movie() {
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
