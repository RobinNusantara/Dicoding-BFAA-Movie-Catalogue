package com.informatika.umm.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.ui.movies.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/11/2020 01 2020
 * 13:14 Sat
 **/
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieList = new ArrayList<>();

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setFavorite(List<Movie> movieList) {
        this.movieList = new ArrayList<>();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getFavorite() {
        return (ArrayList<Movie>) movieList;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        String url = BuildConfig.IMAGE_URL + movie.getMoviePoster();
        Glide.with(holder.itemView.getContext())
                .load(url)
                .apply(new RequestOptions().override(350, 550))
                .placeholder(R.drawable.glide_placeholder)
                .error(R.drawable.glide_error)
                .transform(new RoundedCorners(32))
                .into(holder.imgMoviePoster);

        holder.txtMovieTitle.setText(movie.getMovieTitle());
        holder.txtMovieRelease.setText(movie.getMovieRelease());
        holder.txtMovieScore.setText(String.valueOf(movie.getMovieScore()));
        holder.txtMovieOverview.setText(movie.getMovieOverview());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                context.startActivity(intent);
            }
        });

        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMoviePoster;
        TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieOverview;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
            txtMovieTitle = itemView.findViewById(R.id.txt_movie_title);
            txtMovieRelease = itemView.findViewById(R.id.txt_movie_release_date);
            txtMovieScore = itemView.findViewById(R.id.txt_movie_score);
            txtMovieOverview = itemView.findViewById(R.id.txt_movie_overview);
        }
    }
}
