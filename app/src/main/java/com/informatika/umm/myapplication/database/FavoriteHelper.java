package com.informatika.umm.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.informatika.umm.myapplication.model.Movie;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.informatika.umm.myapplication.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.informatika.umm.myapplication.database.DatabaseContract.MovieColumns.POSTER;
import static com.informatika.umm.myapplication.database.DatabaseContract.MovieColumns.RATING;
import static com.informatika.umm.myapplication.database.DatabaseContract.MovieColumns.RELEASE;
import static com.informatika.umm.myapplication.database.DatabaseContract.MovieColumns.TITLE;
import static com.informatika.umm.myapplication.database.DatabaseContract.TABLE_MOVIE;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/10/2020 01 2020
 * 14:03 Fri
 **/
public class FavoriteHelper {
    private static final String DATABASE_TABLE_MOVIE = TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public List<Movie> movie() {
        List<Movie> list = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE_MOVIE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setMovieId(cursor.getInt(cursor.getColumnIndex(_ID)));
                movie.setMovieTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                movie.setMovieRelease(cursor.getString(cursor.getColumnIndex(RELEASE)));
                movie.setMovieScore(cursor.getDouble(cursor.getColumnIndex(RATING)));
                movie.setMovieOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
                movie.setMoviePoster(cursor.getString(cursor.getColumnIndex(POSTER)));
                list.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public boolean isExist(Movie movie) {
        database = databaseHelper.getWritableDatabase();
        String QUERY = "SELECT * FROM " + TABLE_MOVIE + " WHERE " + _ID + "=" + movie.getMovieId();

        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insert(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(_ID, movie.getMovieId());
        values.put(TITLE, movie.getMovieTitle());
        values.put(RELEASE, movie.getMovieRelease());
        values.put(RATING, movie.getMovieScore());
        values.put(OVERVIEW, movie.getMovieOverview());
        values.put(POSTER, movie.getMoviePoster());
        return database.insert(DATABASE_TABLE_MOVIE, null, values);
    }

    public int delete(int id) {
        return database.delete(DATABASE_TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }
}
