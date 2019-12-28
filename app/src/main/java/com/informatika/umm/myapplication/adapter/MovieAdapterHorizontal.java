package com.informatika.umm.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.ui.movies.MoviesDetailActivity;

import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/27/2019 12 2019
 * 21:18 Fri
 **/
public class MovieAdapterHorizontal extends RecyclerView.Adapter<MovieAdapterHorizontal.MovieViewHolder> {

    private Context context;
    private List<Movie> listMovies;

    public MovieAdapterHorizontal(Context context, List<Movie> listMovies) {
        this.listMovies = listMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapterHorizontal.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movies_popular, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterHorizontal.MovieViewHolder holder, int position) {

        final Movie movie = listMovies.get(position);
        String urlPoster = BuildConfig.IMAGE_URL + movie.getMoviePoster();
        Glide.with(holder.itemView.getContext())
                .load(urlPoster)
                .apply(new RequestOptions().override(350, 550))
                .transform(new RoundedCorners(32))
                .into(holder.imgMoviePoster);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(context, MoviesDetailActivity.class);
                intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movie);
                context.startActivity(intent);
            }
        });

        holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMoviePoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
        }
    }
}