package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/24/2019 12 2019
 * 23:16 Tue
 **/
public class MovieResponse implements Parcelable {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<Movie> results;
    @SerializedName("total_results")
    private int totalResult;
    @SerializedName("total_pages")
    private int totalPages;

    public ArrayList<Movie> getResults() {
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResult);
        dest.writeInt(this.totalPages);
    }

    private MovieResponse(Parcel in) {
        this.page = in.readInt();
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.totalResult = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Parcelable.Creator<MovieResponse> CREATOR = new Parcelable.Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel source) {
            return new MovieResponse(source);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };
}