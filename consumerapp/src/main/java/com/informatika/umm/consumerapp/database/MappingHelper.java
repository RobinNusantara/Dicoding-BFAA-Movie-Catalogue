package com.informatika.umm.consumerapp.database;

import android.database.Cursor;

import com.informatika.umm.consumerapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/13/2020 02 2020
 * 17:40 Thu
 **/
public class MappingHelper {

    public static List<Movie> mapCursorToArrayList(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            String movieTitle = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_TITLE));
            String movieRelease = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_RELEASE));
            String moviePoster = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_POSTER));
            Double movieScore = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_SCORE));
            String movieOverview = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_OVERVIEW));
            String movieType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppDatabase.MOVIE_TYPE));
            movies.add(new Movie(movieTitle, moviePoster, movieRelease, movieScore, movieOverview, movieType));
        }
        return movies;
    }

    public static List<Movie> filterFavorite(List<Movie> movies, String type) {
        List<Movie> filterFavorite = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getMovieType().equals(type)) {
                filterFavorite.add(movies.get(i));
            }
        }
        return filterFavorite;
    }
}
