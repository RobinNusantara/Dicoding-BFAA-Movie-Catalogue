package com.informatika.umm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Cast implements Parcelable {

    @SerializedName("cast_id")
    private int castId;

    @SerializedName("character")
    private String castCharacter;

    @SerializedName("name")
    private String CastName;

    @SerializedName("profile_path")
    private String castProfile;

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCastCharacter() {
        return castCharacter;
    }

    public void setCastCharacter(String castCharacter) {
        this.castCharacter = castCharacter;
    }

    public String getCastName() {
        return CastName;
    }

    public void setCastName(String castName) {
        CastName = castName;
    }

    public String getCastProfile() {
        return castProfile;
    }

    public void setCastProfile(String castProfile) {
        this.castProfile = castProfile;
    }

    public Cast() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.castId);
        dest.writeString(this.castCharacter);
        dest.writeString(this.CastName);
        dest.writeString(this.castProfile);
    }

    private Cast(Parcel in) {
        this.castId = in.readInt();
        this.castCharacter = in.readString();
        this.CastName = in.readString();
        this.castProfile = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel source) {
            return new Cast(source);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };
}
