package com.informatika.umm.myapplication.util;

import android.content.Context;

import com.informatika.umm.myapplication.R;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/31/2019 12 2019
 * 13:40 Tue
 **/
public class Utils {

    public static String getRuntime(Context context, int time) {
        String hours = String.valueOf(time / 60);
        String minutes = String.valueOf(time % 60);
        return context.getString(R.string.str_movie_runtime, hours, minutes);
    }
}
