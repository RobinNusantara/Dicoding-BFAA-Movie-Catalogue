package com.informatika.umm.consumerapp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.informatika.umm.consumerapp.R;
import com.informatika.umm.consumerapp.adapter.MovieListAdapter;
import com.informatika.umm.consumerapp.helper.DataObserver;
import com.informatika.umm.consumerapp.helper.DatabaseContract;
import com.informatika.umm.consumerapp.model.Movie;
import com.informatika.umm.consumerapp.task.GetFavoriteTask;
import com.informatika.umm.consumerapp.util.FavoriteListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FavoriteListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        setupRecyclerView();
        setupRefreshLayout();
        initViewModel();
        initFavoriteTask();
        registerContentObserver();
    }

    @Override
    public void onFavoriteLoad(List<Movie> movies) {
        viewModel.setMovieList(movies);
    }

    private void bindView() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView = findViewById(R.id.rv_favorite);
    }

    private void setupRecyclerView() {
        adapter = new MovieListAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setupRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initFavoriteTask();
            }
        });
    }

    public void initFavoriteTask() {
        new GetFavoriteTask(this, this).execute();
    }

    private void registerContentObserver() {
        HandlerThread thread = new HandlerThread("DataObserver");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        getContentResolver().registerContentObserver(
                DatabaseContract.AppDatabase.CONTENT_URI,
                true,
                new DataObserver.Observer(handler, this));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        viewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovie(movies);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
