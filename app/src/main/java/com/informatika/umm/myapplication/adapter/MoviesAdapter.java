package com.informatika.umm.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.ui.movies.MoviesDetailActivity;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movies;

import java.util.ArrayList;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/29/2019 11 2019
 * 22:21 Fri
 **/
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context context;
    private ArrayList<Movies> listMovies = new ArrayList<>();

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Movies> listMovies) {
        this.listMovies.clear();
        this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movies, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {

        final Movies movies = listMovies.get(position);
        String urlPoster = BuildConfig.IMAGE_URL + movies.getMoviePoster();
        Glide.with(holder.itemView.getContext())
                .load(urlPoster)
                .apply(new RequestOptions().override(350, 550))
                .transform(new RoundedCorners(32))
                .into(holder.imgMoviePoster);


        holder.txtMovieTitle.setText(movies.getMovieTitle());
        holder.txtMovieRelease.setText(movies.getMovieRelease());

        if (movies.getMovieScore() >= 1 && movies.getMovieScore() < 2.0) {
            holder.ratingBar.setRating(1.0f);
        } else if (movies.getMovieScore() >= 2.0 && movies.getMovieScore() < 5.0) {
            holder.ratingBar.setRating(2.0f);
        } else if (movies.getMovieScore() >= 5.0 && movies.getMovieScore() < 6.0) {
            holder.ratingBar.setRating(2.5f);
        } else if (movies.getMovieScore() >= 6.0 && movies.getMovieScore() < 7.0) {
            holder.ratingBar.setRating(3.0f);
        } else if (movies.getMovieScore() >= 7.0 && movies.getMovieScore() < 8.0) {
            holder.ratingBar.setRating(3.5f);
        } else if (movies.getMovieScore() >= 8.0 && movies.getMovieScore() < 9.0) {
            holder.ratingBar.setRating(4.0f);
        } else if (movies.getMovieScore() >= 9.0) {
            holder.ratingBar.setRating(4.5f);
        } else if (movies.getMovieScore() >= 9.5) {
            holder.ratingBar.setRating(5.0f);
        } else {
            holder.ratingBar.setRating(0);
        }

        String moviesScore = Double.toString(movies.getMovieScore());
        holder.txtMovieScore.setText(moviesScore);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(context, MoviesDetailActivity.class);
                intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movies);
                context.startActivity(intent);
            }
        });

        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMoviePoster;
        TextView txtMovieTitle, txtMovieScore, txtMovieRelease;
        RatingBar ratingBar;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.rate_movie);
            imgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
            txtMovieTitle = itemView.findViewById(R.id.txt_movie_title);
            txtMovieRelease = itemView.findViewById(R.id.txt_movie_release_date);
            txtMovieScore = itemView.findViewById(R.id.txt_movie_score);
        }
    }
}
