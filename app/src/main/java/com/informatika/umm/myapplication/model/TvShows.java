package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/30/2019 11 2019
 * 10:22 Sat
 **/
public class TvShows implements Parcelable {

    private int tvshowsPoster;
    private String tvshowsTitles;
    private String tvshowsScore;
    private String tvshowsRelease;


    public int getTvshowsPoster() {
        return tvshowsPoster;
    }

    public void setTvshowsPoster(int tvshowsPoster) {
        this.tvshowsPoster = tvshowsPoster;
    }

    public String getTvshowsTitles() {
        return tvshowsTitles;
    }

    public void setTvshowsTitles(String tvshowsTitles) {
        this.tvshowsTitles = tvshowsTitles;
    }

    public String getTvshowsScore() {
        return tvshowsScore;
    }

    public void setTvshowsScore(String tvshowsScore) {
        this.tvshowsScore = tvshowsScore;
    }

    public String getTvshowsRelease() {
        return tvshowsRelease;
    }

    public void setTvshowsRelease(String tvshowsRelease) {
        this.tvshowsRelease = tvshowsRelease;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tvshowsPoster);
        dest.writeString(this.tvshowsTitles);
        dest.writeString(this.tvshowsScore);
        dest.writeString(this.tvshowsRelease);
    }

    public TvShows() {
    }

    protected TvShows(Parcel in) {
        this.tvshowsPoster = in.readInt();
        this.tvshowsTitles = in.readString();
        this.tvshowsScore = in.readString();
        this.tvshowsRelease = in.readString();
    }

    public static final Parcelable.Creator<TvShows> CREATOR = new Parcelable.Creator<TvShows>() {
        @Override
        public TvShows createFromParcel(Parcel source) {
            return new TvShows(source);
        }

        @Override
        public TvShows[] newArray(int size) {
            return new TvShows[size];
        }
    };
}
