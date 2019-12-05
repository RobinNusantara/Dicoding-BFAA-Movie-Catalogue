package com.informatika.umm.myapplication.ui.tvshows;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.TvShowsAdapter;
import com.informatika.umm.myapplication.model.TvShows;

import java.util.ArrayList;

public class TvShowsFragment extends Fragment {

    private String[] dataTvShowsTitle;
    private String[] dataTvShowsRelease;
    private String[] dataTvShowsScore;
    private String[] dataTvShowsRuntime;
    private String[] dataTvShowsOverview;
    private TypedArray dataTvShowsPoster;
    private TypedArray dataTvShowsBackdrop;
    private TvShowsAdapter tvShowsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshows, container, false);
        RecyclerView recyclerViewTvShows;
        tvShowsAdapter = new TvShowsAdapter(getActivity());
        recyclerViewTvShows = view.findViewById(R.id.rv_tv_shows_list);
        recyclerViewTvShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTvShows.setAdapter(tvShowsAdapter);

        prepare();
        addItem();

        return view;
    }

    private void prepare() {
        dataTvShowsTitle = getResources().getStringArray(R.array.item_tv_shows_title);
        dataTvShowsPoster = getResources().obtainTypedArray(R.array.item_tv_shows_poster);
        dataTvShowsBackdrop = getResources().obtainTypedArray(R.array.item_tv_shows_backdrop);
        dataTvShowsRelease = getResources().getStringArray(R.array.item_tv_shows_release);
        dataTvShowsScore = getResources().getStringArray(R.array.item_tv_shows_score);
        dataTvShowsRuntime = getResources().getStringArray(R.array.item_tv_shows_runtime);
        dataTvShowsOverview = getResources().getStringArray(R.array.item_tv_shows_overview);
    }

    private void addItem() {
        ArrayList<TvShows> listTvShows = new ArrayList<>();
        for (int position = 0; position < dataTvShowsTitle.length; position++) {
            TvShows tvShows = new TvShows();
            tvShows.setTvShowsPoster(dataTvShowsPoster.getResourceId(position, -1));
            tvShows.setTvShowsBackdrop(dataTvShowsBackdrop.getResourceId(position, -1));
            tvShows.setTvshowsTitles(dataTvShowsTitle[position]);
            tvShows.setTvShowsRelease(dataTvShowsRelease[position]);
            tvShows.setTvShowsScore(dataTvShowsScore[position]);
            tvShows.setTvShowsRuntime(dataTvShowsRuntime[position]);
            tvShows.setTvShowsOverview(dataTvShowsOverview[position]);
            listTvShows.add(tvShows);
        }
        tvShowsAdapter.setTvShows(listTvShows);
    }
}