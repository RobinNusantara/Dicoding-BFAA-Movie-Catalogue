package com.informatika.umm.myapplication.ui.fragment.tvshows;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.TvShowsAdapter;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.ui.activity.search.tvshows.SearchTvShowsActivity;

import java.util.ArrayList;
import java.util.List;

public class TvShowsFragment extends Fragment {

    private ShimmerFrameLayout shimmerFrameLayout;
    private TvShowsAdapter listAdapter;
    private TvShowsViewModel viewModel;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView rvDiscover;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvshows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        bindView(view);
        setupViewModel();
        showRecyclerView();
        viewModel.loadDiscoverTvShow();
        shimmerFrameLayout.startShimmer();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);
        viewModel.getTvShow().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                listAdapter.setMovie(movieList);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    private void showRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listAdapter = new TvShowsAdapter(getContext(), movieList);
        rvDiscover.setHasFixedSize(true);
        rvDiscover.setLayoutManager(layoutManager);
        rvDiscover.setAdapter(listAdapter);
    }

    private void bindView(View view) {
        shimmerFrameLayout = view.findViewById(R.id.shimmer_container);
        rvDiscover = view.findViewById(R.id.rv_movies_now_playing);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.btn_search) {
            intent = new Intent(getActivity(), SearchTvShowsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}