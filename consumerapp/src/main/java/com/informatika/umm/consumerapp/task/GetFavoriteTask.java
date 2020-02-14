package com.informatika.umm.consumerapp.task;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.informatika.umm.consumerapp.database.DatabaseContract;
import com.informatika.umm.consumerapp.database.MappingHelper;
import com.informatika.umm.consumerapp.model.Movie;
import com.informatika.umm.consumerapp.util.FavoriteListener;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/13/2020 02 2020
 * 17:40 Thu
 **/
public class GetFavoriteTask extends AsyncTask<Void, Void, List<Movie>> {

    private WeakReference<Context> contextWeakReference;
    private WeakReference<FavoriteListener> favoriteListener;

    public GetFavoriteTask(Context contextWeakReference, FavoriteListener favoriteListener) {
        this.contextWeakReference = new WeakReference<>(contextWeakReference);
        this.favoriteListener = new WeakReference<>(favoriteListener);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        favoriteListener.get().preExecute();
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        Cursor cursor = contextWeakReference.get()
                .getApplicationContext()
                .getContentResolver()
                .query(DatabaseContract.AppDatabase.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
        return MappingHelper.mapCursorToArrayList(cursor);
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        favoriteListener.get().onPostExecute(movies);
    }
}
