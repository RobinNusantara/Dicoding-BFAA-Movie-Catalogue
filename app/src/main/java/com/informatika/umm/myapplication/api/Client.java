package com.informatika.umm.myapplication.api;

import com.informatika.umm.myapplication.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/28/2019 12 2019
 * 10:52 Sat
 **/
public class Client {

    private static final String url = BuildConfig.MOVIE_URL;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
