package com.informatika.umm.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Genre;

import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 12/31/2019 12 2019
 * 11:55 Tue
 **/
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenresViewHolder> {

    private List<Genre> genres;

    public GenreAdapter( List<Genre> genres) {
        this.genres = genres;
    }

    public void setGenre(List<Genre> genres) {
        this.genres.clear();
        this.genres.addAll(genres);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenreAdapter.GenresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_genre, viewGroup, false);
        return new GenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.GenresViewHolder holder, int position) {

        final Genre genre = genres.get(position);
        holder.txtMovieGenre.setText(genre.getName());
        holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    class GenresViewHolder extends RecyclerView.ViewHolder {

        TextView txtMovieGenre;

        GenresViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieGenre = itemView.findViewById(R.id.txt_movie_genre);
        }
    }
}
