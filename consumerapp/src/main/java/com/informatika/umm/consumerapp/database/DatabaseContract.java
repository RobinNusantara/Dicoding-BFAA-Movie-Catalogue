package com.informatika.umm.consumerapp.database;

import android.net.Uri;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/13/2020 02 2020
 * 17:40 Thu
 **/
public class DatabaseContract {

    private static final String AUTHORITY = "com.informatika.umm.myapplication.provider";
    private static final String SCHEME = "content";

    public static final class AppDatabase {
        static final String TABLE_NAME = "movie";
        static final String MOVIE_TITLE = "title";
        static final String MOVIE_RELEASE = "release";
        static final String MOVIE_POSTER = "poster";
        static final String MOVIE_SCORE = "score";
        static final String MOVIE_OVERVIEW = "description";
        static final String MOVIE_TYPE = "type";

        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
