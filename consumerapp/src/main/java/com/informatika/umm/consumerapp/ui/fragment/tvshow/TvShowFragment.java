package com.informatika.umm.consumerapp.ui.fragment.tvshow;


import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.informatika.umm.consumerapp.R;
import com.informatika.umm.consumerapp.adapter.ListAdapter;
import com.informatika.umm.consumerapp.database.DataObserver;
import com.informatika.umm.consumerapp.database.DatabaseContract;
import com.informatika.umm.consumerapp.database.MappingHelper;
import com.informatika.umm.consumerapp.model.Movie;
import com.informatika.umm.consumerapp.task.GetFavoriteTask;
import com.informatika.umm.consumerapp.util.FavoriteListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements FavoriteListener {

    private static final String KEY = "shows";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ListAdapter adapter;

    public TvShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupRecyclerView();
        setupRefreshLayout();
        initFavoriteTask();
        registerContentObserver();
    }

    private void bindView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.rv_favorite);
    }

    private void setupRecyclerView() {
        adapter = new ListAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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

    private void initFavoriteTask() {
        new GetFavoriteTask(getContext(), this).execute();
    }

    private void registerContentObserver() {
        HandlerThread thread = new HandlerThread("DataObserver");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        if (getActivity() != null) {
            getActivity().getContentResolver().registerContentObserver(
                    DatabaseContract.AppDatabase.CONTENT_URI,
                    true,
                    new DataObserver.Observer(handler, getActivity())
            );
        }
    }

    @Override
    public void preExecute() {
    }

    @Override
    public void onPostExecute(List<Movie> movies) {
        if (movies.size() > 0) {
            adapter.setMovie(MappingHelper.filterFavorite(movies, KEY));
            swipeRefreshLayout.setRefreshing(false);
        } else {
            adapter.setMovie(new ArrayList<Movie>());
        }
    }
}
