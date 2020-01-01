package com.informatika.umm.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movie;

import java.util.ArrayList;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/1/2020 01 2020
 * 11:55 Wed
 **/
public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;

    public MovieCardAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
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
                .into(holder.imgMoviePoster);

        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMoviePoster;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
        }
    }
}
