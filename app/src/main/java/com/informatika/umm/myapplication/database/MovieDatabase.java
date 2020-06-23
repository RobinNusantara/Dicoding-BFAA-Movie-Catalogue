package com.informatika.umm.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.informatika.umm.myapplication.model.Movie;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/10/2020 02 2020
 * 11:11 Mon
 **/
@Database(entities = Movie.class, exportSchema = false, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}
