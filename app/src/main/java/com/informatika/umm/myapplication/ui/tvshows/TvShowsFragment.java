package com.informatika.umm.myapplication.ui.tvshows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.TvShowsAdapter;
import com.informatika.umm.myapplication.model.TvShows;

import java.util.ArrayList;

public class TvShowsFragment extends Fragment {

    private TvShowsAdapter tvShowsAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshows, container, false);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_container);
        shimmerFrameLayout.startShimmer();
        RecyclerView recyclerViewTvShows;
        tvShowsAdapter = new TvShowsAdapter(getActivity());
        recyclerViewTvShows = view.findViewById(R.id.rv_tv_shows_list);
        recyclerViewTvShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTvShows.setAdapter(tvShowsAdapter);

        TvShowsViewModel tvShowsViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);
        tvShowsViewModel.getTvShows().observe(getViewLifecycleOwner(), getTvShows);
        tvShowsViewModel.setTvShows();

        return view;
    }

    private Observer<ArrayList<TvShows>> getTvShows = new Observer<ArrayList<TvShows>>() {
        @Override
        public void onChanged(ArrayList<TvShows> tvShows) {
            if (tvShows != null) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                tvShowsAdapter.setTvShows(tvShows);
            }
        }
    };
}