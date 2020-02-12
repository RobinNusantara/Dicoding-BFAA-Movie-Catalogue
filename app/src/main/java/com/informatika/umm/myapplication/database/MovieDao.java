package com.informatika.umm.myapplication.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.informatika.umm.myapplication.model.Movie;

import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/10/2020 02 2020
 * 11:10 Mon
 **/
@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie WHERE type=:type")
    List<Movie> getMovieList(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Movie movie);

    @Query("DELETE FROM movie WHERE movieId=:id")
    void removeFavorite(int id);

    @Query("SELECT * FROM movie WHERE movieId=:id")
    Movie getMovie(int id);

    @Query("SELECT * FROM movie")
    Cursor getMovieCursor();
}
