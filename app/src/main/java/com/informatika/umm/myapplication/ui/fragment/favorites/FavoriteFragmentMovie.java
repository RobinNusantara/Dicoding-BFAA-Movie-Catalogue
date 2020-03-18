package com.informatika.umm.myapplication.ui.fragment.favorites;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.MovieListAdapter;
import com.informatika.umm.myapplication.database.RoomClient;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.ui.activity.detail.movies.DetailMovieActivity;
import com.informatika.umm.myapplication.util.ItemClickListener;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragmentMovie extends Fragment implements ItemClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private GetMovieFavoriteTask favoriteTask;
    private MovieListAdapter adapter;
    private FavoriteViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initFavoriteTask();
        setupViewModel();
        setupRecyclerView();
        setupRefreshLayout();
        favoriteTask.execute();
    }

    private void bindView(View view) {
        recyclerView = view.findViewById(R.id.rv_favorite);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FavoriteViewModel.class);
        viewModel.getMovieList().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                adapter.setMovie(movieList);
            }
        });
    }

    private void initFavoriteTask() {
        favoriteTask = new GetMovieFavoriteTask(this);
    }

    private void setupRecyclerView() {
        adapter = new MovieListAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    private void setupRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFavoriteMovie();
            }
        });
    }

    private void refreshFavoriteMovie() {
        favoriteTask = null;
        initFavoriteTask();
        favoriteTask.execute();
    }

    @Override
    public void onItemClicked(Movie movie) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
            getActivity().startActivity(intent);
        }
    }

    private static class GetMovieFavoriteTask extends AsyncTask<Void, Void, List<Movie>> {

        private WeakReference<FavoriteFragmentMovie> fragment;

        GetMovieFavoriteTask(FavoriteFragmentMovie fragment) {
            this.fragment = new WeakReference<>(fragment);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            String KEY = Objects.requireNonNull(fragment.get().getContext()).getString(R.string.str_key_movies);
            return RoomClient
                    .getInstance(fragment.get().getContext())
                    .getMovieDatabase()
                    .getMovieDao()
                    .getMovieList(KEY);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            fragment.get().viewModel.setMovieList(movies);
            fragment.get().swipeRefreshLayout.setRefreshing(false);
        }
    }
}