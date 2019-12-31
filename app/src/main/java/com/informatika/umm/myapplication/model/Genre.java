package com.informatika.umm.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/31/2019 12 2019
 * 11:30 Tue
 **/
public class Genre {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

}
