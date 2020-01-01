package com.informatika.umm.myapplication.util;

import android.content.Context;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Genre;

import java.util.ArrayList;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/31/2019 12 2019
 * 13:40 Tue
 **/
public class StringUtils {

    public static String getRuntime(Context context, int time) {
        String hours = String.valueOf(time / 60);
        String minutes = String.valueOf(time % 60);
        return context.getString(R.string.str_movie_runtime, hours, minutes);
    }

    public static String getGenre(ArrayList<Genre> genres) {
        StringBuilder result = new StringBuilder("-");
        if (!genres.isEmpty()) {
            result = new StringBuilder();
            for (int i = 0; i < genres.size(); i++) {
                if (genres.get(i) == genres.get(genres.size() - 1)) {
                    result.append(genres.get(i).getName());
                } else {
                    result.append(genres.get(i).getName()).append(", ");
                }
            }
        }
        return result.toString();
    }
}
