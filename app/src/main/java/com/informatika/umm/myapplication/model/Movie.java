package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/30/2019 11 2019
 * 10:22 Sat
 **/
@Entity(tableName = "movie")
public class Movie implements Parcelable {

    @SerializedName("id")
    @PrimaryKey
    private int movieId;

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop")
    private String movieBackdrop;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster")
    private String moviePoster;

    @SerializedName(value = "title", alternate = "name")
    @ColumnInfo(name = "title")
    private String movieTitle;

    @SerializedName("vote_average")
    @ColumnInfo(name = "score")
    private Double movieScore;

    @SerializedName("vote_count")
    @ColumnInfo(name = "review")
    private int movieReview;

    @SerializedName(value = "release_date", alternate = "first_air_date")
    @ColumnInfo(name = "release")
    private String movieRelease;

    @SerializedName("overview")
    @ColumnInfo(name = "description")
    private String movieOverview;

    @SerializedName("genres")
    @Ignore
    private List<Genre> movieGenre;

    @SerializedName("runtime")
    @Ignore
    private int movieRuntime;

    @SerializedName("episode_run_time")
    @Ignore
    private List<Integer> episodeRunTime;

    @SerializedName("status")
    @Ignore
    private String movieStatus;

    @ColumnInfo(name = "isFavorite")
    private Boolean isFavorite = false;

    @ColumnInfo(name = "type")
    private String type;

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

    public int getMovieReview() {
        return movieReview;
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

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public String getMovieStatus() {
        return movieStatus;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public String getType() {
        return type;
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

    public void setMovieReview(int movieReview) {
        this.movieReview = movieReview;
    }

    public void setIsFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public void setType(String type) {
        this.type = type;
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
        dest.writeInt(this.movieReview);
        dest.writeString(this.movieRelease);
        dest.writeString(this.movieOverview);
        dest.writeTypedList(this.movieGenre);
        dest.writeInt(this.movieRuntime);
        dest.writeList(this.episodeRunTime);
        dest.writeString(this.movieStatus);
        dest.writeValue(this.isFavorite);
        dest.writeString(this.type);
    }

    protected Movie(Parcel in) {
        this.movieId = in.readInt();
        this.movieBackdrop = in.readString();
        this.moviePoster = in.readString();
        this.movieTitle = in.readString();
        this.movieScore = (Double) in.readValue(Double.class.getClassLoader());
        this.movieReview = in.readInt();
        this.movieRelease = in.readString();
        this.movieOverview = in.readString();
        this.movieGenre = in.createTypedArrayList(Genre.CREATOR);
        this.movieRuntime = in.readInt();
        this.episodeRunTime = new ArrayList<>();
        in.readList(this.episodeRunTime, Integer.class.getClassLoader());
        this.movieStatus = in.readString();
        this.isFavorite = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.type = in.readString();
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
