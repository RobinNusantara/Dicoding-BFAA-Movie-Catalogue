package com.informatika.umm.myapplication.database;

import android.content.Context;

import androidx.room.Room;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/10/2020 02 2020
 * 11:11 Mon
 **/
public class RoomClient {

    private static final String DATABASE_NAME = "movie_db";
    private static RoomClient INSTANCE;
    private MovieDatabase movieDatabase;

    private RoomClient(Context context) {
        movieDatabase = Room.databaseBuilder(
                context.getApplicationContext(),
                MovieDatabase.class,
                DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public static synchronized RoomClient getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RoomClient(context);
        }
        return INSTANCE;
    }

    public MovieDatabase getMovieDatabase() {
        return movieDatabase;
    }
}
