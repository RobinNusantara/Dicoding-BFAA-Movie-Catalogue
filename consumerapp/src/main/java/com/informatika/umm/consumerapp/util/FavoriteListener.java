package com.informatika.umm.consumerapp.util;

import com.informatika.umm.consumerapp.model.Movie;

import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/13/2020 02 2020
 * 17:52 Thu
 **/
public interface FavoriteListener {
    void onFavoriteLoad(List<Movie> movies);
}
