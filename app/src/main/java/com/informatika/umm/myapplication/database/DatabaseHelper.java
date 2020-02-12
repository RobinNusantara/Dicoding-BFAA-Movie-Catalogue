package com.informatika.umm.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.informatika.umm.myapplication.database.DatabaseContract.MovieColumns;
import static com.informatika.umm.myapplication.database.DatabaseContract.TvShowColumns;
import static com.informatika.umm.myapplication.database.DatabaseContract.TABLE_MOVIE;
import static com.informatika.umm.myapplication.database.DatabaseContract.TABLE_TVSHOW;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/9/2020 01 2020
 * 15:41 Thu
 **/
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "favoritedb";
    private static final int DATABASE_VERSION = 8;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s DOUBLE NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            MovieColumns._ID,
            MovieColumns.TITLE,
            MovieColumns.RELEASE,
            MovieColumns.RATING,
            MovieColumns.OVERVIEW,
            MovieColumns.REVIEW,
            MovieColumns.POSTER,
            MovieColumns.BACKDROP
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s DOUBLE NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_TVSHOW,
            TvShowColumns._ID,
            TvShowColumns.TITLE,
            TvShowColumns.RELEASE,
            TvShowColumns.RATING,
            TvShowColumns.OVERVIEW,
            TvShowColumns.REVIEW,
            TvShowColumns.POSTER,
            TvShowColumns.BACKDROP
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOW);
    }
}
