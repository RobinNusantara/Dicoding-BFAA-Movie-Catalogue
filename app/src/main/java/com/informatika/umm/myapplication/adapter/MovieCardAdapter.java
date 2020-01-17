package com.informatika.umm.myapplication.adapter;

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
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movie;

import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/1/2020 01 2020
 * 11:55 Wed
 **/
public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.CardViewHolder> {

    private List<Movie> movieList;

    public MovieCardAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void setMovie(List<Movie> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieCardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_card, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCardAdapter.CardViewHolder holder, int position) {
        final Movie movie = movieList.get(position);

        String urlPoster = BuildConfig.IMAGE_URL + movie.getMoviePoster();
        Glide.with(holder.itemView.getContext())
                .load(urlPoster)
                .apply(new RequestOptions().override(350, 550))
                .transform(new RoundedCorners(32))
                .placeholder(R.drawable.item_glide_placeholder)
                .error(R.drawable.item_glide_error)
                .into(holder.imgMoviePoster);

        holder.txtMovieTitle.setText(movie.getMovieTitle());
        holder.txtMovieScore.setText(String.valueOf(movie.getMovieScore()));
        holder.ratingBar.setRating(movie.getRating());

        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        TextView txtMovieScore, txtMovieTitle;
        ImageView imgMoviePoster;
        RatingBar ratingBar;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
            txtMovieTitle = itemView.findViewById(R.id.txt_movie_title);
            ratingBar = itemView.findViewById(R.id.rate_movie);
            txtMovieScore = itemView.findViewById(R.id.txt_movie_score);
        }
    }
}
