package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/30/2019 11 2019
 * 10:22 Sat
 **/
public class MovieItem implements Parcelable {

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

    private MovieItem(Parcel in) {
        this.moviePoster = in.readString();
        this.movieBackdrop = in.readString();
        this.movieTitle = in.readString();
        this.movieScore = in.readDouble();
        this.movieRelease = in.readString();
        this.movieOverview = in.readString();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

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
}
