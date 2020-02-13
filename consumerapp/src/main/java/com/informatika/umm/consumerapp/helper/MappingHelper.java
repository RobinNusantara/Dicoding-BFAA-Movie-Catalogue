package com.informatika.umm.consumerapp.helper;

import android.database.Cursor;

import com.informatika.umm.consumerapp.model.Movie;

import java.util.ArrayList;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/13/2020 02 2020
 * 17:40 Thu
 **/
public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Movie> movies = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            String movieTitle = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_TITLE));
            String movieRelease = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_RELEASE));
            String moviePoster = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_POSTER));
            Double movieScore = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_SCORE));
            movies.add(new Movie(movieTitle, moviePoster, movieRelease, movieScore));
        }
        return movies;
    }
}
