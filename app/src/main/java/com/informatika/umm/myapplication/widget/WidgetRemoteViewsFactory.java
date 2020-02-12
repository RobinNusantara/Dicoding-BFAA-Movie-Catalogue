package com.informatika.umm.myapplication.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.database.RoomClient;
import com.informatika.umm.myapplication.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/7/2020 02 2020
 * 20:35 Fri
 **/
public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<Movie> movieList = new ArrayList<>();

    WidgetRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        String KEY = "movies";
        movieList = RoomClient.getInstance(context)
                .getMovieDatabase()
                .getMovieDao()
                .getMovieList(KEY);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_row_widget);
        if (!movieList.isEmpty()) {
            Movie movie = movieList.get(position);
            try {
                Bitmap bitmap = Glide.with(context)
                        .asBitmap()
                        .load(movie.getMoviePoster())
                        .fitCenter()
                        .submit()
                        .get();
                remoteViews.setImageViewBitmap(R.id.img_movie_widget, bitmap);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent();
            intent.putExtra(ImageBannerWidget.EXTRA_MOVIE, movie);
            remoteViews.setOnClickFillInIntent(R.id.container_view_widget, intent);
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
