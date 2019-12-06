package com.informatika.umm.myapplication.ui.tvshows;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.model.TvShows;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/5/2019 12 2019
 * 13:57 Thu
 **/
public class TvShowsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TvShows>> listTvShows = new MutableLiveData<>();

    public void setTvShows() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShows> listItem = new ArrayList<>();
        String url = BuildConfig.TV_URL + BuildConfig.API_KEY + BuildConfig.LANGUAGE;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonTvShows = list.getJSONObject(i);
                        TvShows tvShows = new TvShows(jsonTvShows);
                        listItem.add(tvShows);
                    }
                    listTvShows.postValue(listItem);
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

    public LiveData<ArrayList<TvShows>> getTvShows() {
        return listTvShows;
    }
}
