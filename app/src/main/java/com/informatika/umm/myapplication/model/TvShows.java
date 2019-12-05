package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/30/2019 11 2019
 * 10:22 Sat
 **/
public class TvShows implements Parcelable {

    private int tvShowsPoster;
    private int tvShowsBackdrop;
    private String tvShowsTitle;
    private String tvShowsScore;
    private String tvShowsRelease;
    private String tvShowsRuntime;
    private String tvShowsOverview;

    public int getTvShowsPoster() {
        return tvShowsPoster;
    }

    public void setTvShowsPoster(int tvShowsPoster) {
        this.tvShowsPoster = tvShowsPoster;
    }

    public String getTvshowsTitles() {
        return tvShowsTitle;
    }

    public void setTvshowsTitles(String tvshowsTitles) {
        this.tvShowsTitle = tvshowsTitles;
    }

    public String getTvShowsScore() {
        return tvShowsScore;
    }

    public void setTvShowsScore(String tvShowsScore) {
        this.tvShowsScore = tvShowsScore;
    }

    public String getTvShowsRelease() {
        return tvShowsRelease;
    }

    public void setTvShowsRelease(String tvShowsRelease) {
        this.tvShowsRelease = tvShowsRelease;
    }

    public int getTvShowsBackdrop() {
        return tvShowsBackdrop;
    }

    public void setTvShowsBackdrop(int tvShowsBackdrop) {
        this.tvShowsBackdrop = tvShowsBackdrop;
    }

    public String getTvShowsRuntime() {
        return tvShowsRuntime;
    }

    public void setTvShowsRuntime(String tvShowsRuntime) {
        this.tvShowsRuntime = tvShowsRuntime;
    }

    public String getTvShowsOverview() {
        return tvShowsOverview;
    }

    public void setTvShowsOverview(String tvShowsOverview) {
        this.tvShowsOverview = tvShowsOverview;
    }

    public TvShows() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tvShowsPoster);
        dest.writeInt(this.tvShowsBackdrop);
        dest.writeString(this.tvShowsTitle);
        dest.writeString(this.tvShowsScore);
        dest.writeString(this.tvShowsRelease);
        dest.writeString(this.tvShowsRuntime);
        dest.writeString(this.tvShowsOverview);
    }

    protected TvShows(Parcel in) {
        this.tvShowsPoster = in.readInt();
        this.tvShowsBackdrop = in.readInt();
        this.tvShowsTitle = in.readString();
        this.tvShowsScore = in.readString();
        this.tvShowsRelease = in.readString();
        this.tvShowsRuntime = in.readString();
        this.tvShowsOverview = in.readString();
    }

    public static final Creator<TvShows> CREATOR = new Creator<TvShows>() {
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
