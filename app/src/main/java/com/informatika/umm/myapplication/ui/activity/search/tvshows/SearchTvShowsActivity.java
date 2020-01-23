package com.informatika.umm.myapplication.ui.activity.search.tvshows;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.TvShowsAdapter;
import com.informatika.umm.myapplication.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchTvShowsActivity extends AppCompatActivity {

    Toolbar toolbar;
    SearchView searchView;
    RecyclerView rvSearchTvShows;
    TvShowsAdapter adapter;
    SearchTvShowsViewModel viewModel;
    List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bindView();
        setupToolbar();
        setupSearchView();
        setupViewModel();
        setupRecyclerView();
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.search_bar);
        rvSearchTvShows = findViewById(R.id.rv_search_movie);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconified(false);
            searchView.setQueryHint("Search Shows");
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() >= 3) {
                        viewModel.loadSearchTvShows(newText);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.clearFocus();
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
    }

    private void setupRecyclerView() {
        adapter = new TvShowsAdapter(getApplicationContext(), movieList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSearchTvShows.setHasFixedSize(true);
        rvSearchTvShows.setAdapter(adapter);
        rvSearchTvShows.setLayoutManager(layoutManager);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchTvShowsViewModel.class);
        viewModel.getSearchTvShows().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovie(movies);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
