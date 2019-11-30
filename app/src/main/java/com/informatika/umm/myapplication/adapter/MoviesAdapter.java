package com.informatika.umm.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        this.listMovies = listMovies;
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movies, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        final Movies movies = listMovies.get(position);
        Glide.with(holder.itemView.getContext())
                .load(movies.getMoviePoster())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgMoviePoster);

        holder.txtMovieTitle.setText(movies.getMovieTitle());
        holder.txtMovieRelease.setText(movies.getMovieRelease());
        holder.txtMovieScore.setText(movies.getMovieScore());
        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMoviePoster;
        TextView txtMovieTitle, txtMovieScore, txtMovieRelease;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
            txtMovieTitle = itemView.findViewById(R.id.txt_movie_title);
            txtMovieRelease = itemView.findViewById(R.id.txt_movie_release_date);
            txtMovieScore = itemView.findViewById(R.id.txt_movie_score);
        }
    }
}
