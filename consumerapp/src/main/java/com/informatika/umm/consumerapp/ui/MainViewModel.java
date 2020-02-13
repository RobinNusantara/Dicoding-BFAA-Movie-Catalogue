package com.informatika.umm.consumerapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.informatika.umm.consumerapp.model.Movie;

import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/13/2020 02 2020
 * 19:18 Thu
 **/
public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

    public void setMovieList(List<Movie> movieList) {
        this.movieList.postValue(movieList);
    }

    LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

}
