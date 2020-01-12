package com.informatika.umm.myapplication.database;

import android.provider.BaseColumns;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/9/2020 01 2020
 * 15:35 Thu
 **/
class DatabaseContract {

    static String TABLE_MOVIE = "movie_table";

    static final class MovieColumns implements BaseColumns {
        static String TITLE = "title";
        static String RELEASE = "release";
        static String RATING = "rating";
        static String OVERVIEW = "overview";
        static String POSTER = "poster";
    }

}
