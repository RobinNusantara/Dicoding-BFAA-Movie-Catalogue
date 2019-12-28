package com.informatika.umm.myapplication.ui.movies;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/5/2019 12 2019
 * 12:00 Thu
 **/
public class MoviesViewModel extends ViewModel {

    /*private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listsItem = new ArrayList<>();
        String url = BuildConfig.MOVIE_URL + BuildConfig.API_KEY + BuildConfig.LANGUAGE;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonMovies = list.getJSONObject(i);
                        Movie movie = new Movie(jsonMovies);
                        listsItem.add(movie);
                    }
                    listMovies.postValue(listsItem);
                } catch (Exception e) {
                    Log.d(e.getMessage(), "Exception");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(error.getMessage(), "onFailure");
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }*/
}
