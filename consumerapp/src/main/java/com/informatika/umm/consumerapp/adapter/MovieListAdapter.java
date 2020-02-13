package com.informatika.umm.consumerapp.adapter;

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
import com.informatika.umm.consumerapp.BuildConfig;
import com.informatika.umm.consumerapp.R;
import com.informatika.umm.consumerapp.model.Movie;
import com.informatika.umm.consumerapp.util.FormatDate;

import java.util.ArrayList;
import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/29/2019 11 2019
 * 22:21 Fri
 **/
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MoviesViewHolder> {

    private List<Movie> movieList = new ArrayList<>();

    public void setMovie(List<Movie> movieList) {
        this.movieList = movieList;
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

        final Movie movie = movieList.get(position);
        String url = BuildConfig.IMAGE_URL + movie.getMoviePoster();
        Glide.with(holder.itemView.getContext())
                .load(url)
                .apply(new RequestOptions().override(350, 550))
                .placeholder(R.drawable.item_glide_placeholder)
                .error(R.drawable.item_glide_error)
                .transform(new RoundedCorners(32))
                .into(holder.imgMoviePoster);

        holder.txtMovieTitle.setText(movie.getMovieTitle());
        holder.txtMovieRelease.setText(FormatDate.getFormatReleaseDate(movie.getMovieRelease()));
        holder.ratingBar.setRating(movie.getRating());
        holder.txtMovieScore.setText(String.valueOf(movie.getMovieScore()));

        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
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
