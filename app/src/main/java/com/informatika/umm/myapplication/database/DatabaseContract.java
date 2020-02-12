package com.informatika.umm.myapplication.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/9/2020 01 2020
 * 15:35 Thu
 **/
public class DatabaseContract {

    public static String TABLE_MOVIE = "movie_table";
    static String TABLE_TVSHOW = "tvshow_table";
    public static final String AUTHORITY = "com.informatika.umm.myapplication";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String RELEASE = "release";
        public static String RATING = "rating";
        public static String OVERVIEW = "overview";
        public static String REVIEW = "review";
        public static String POSTER = "poster";
        public static String BACKDROP = "backdrop";

        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    static final class TvShowColumns implements BaseColumns {
        static String TITLE = "title";
        static String RELEASE = "release";
        static String RATING = "rating";
        static String OVERVIEW = "overview";
        static String REVIEW = "review";
        static String POSTER = "poster";
        static String BACKDROP = "backdrop";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInteger(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static Double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}
