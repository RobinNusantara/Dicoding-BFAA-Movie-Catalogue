package com.informatika.umm.myapplication.ui.fragment.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.informatika.umm.myapplication.model.Movie;

import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/10/2020 02 2020
 * 12:25 Mon
 **/
public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

    public void setMovieList(List<Movie> movieList) {
        this.movieList.postValue(movieList);
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }
}
