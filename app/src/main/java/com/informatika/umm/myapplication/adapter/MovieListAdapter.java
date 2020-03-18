package com.informatika.umm.myapplication.adapter;

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
import com.informatika.umm.myapplication.util.FormatDate;
import com.informatika.umm.myapplication.util.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/29/2019 11 2019
 * 22:21 Fri
 **/
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MoviesViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private ItemClickListener clickListener;

    public MovieListAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setMovie(List<Movie> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {
        holder.bindItem(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMoviePoster;
        TextView txtMovieTitle, txtMovieScore, txtMovieRelease, txtMovieOverview;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMoviePoster = itemView.findViewById(R.id.img_movie_poster);
            txtMovieTitle = itemView.findViewById(R.id.txt_movie_title);
            txtMovieRelease = itemView.findViewById(R.id.txt_movie_release_date);
            txtMovieScore = itemView.findViewById(R.id.txt_movie_score);
            txtMovieOverview = itemView.findViewById(R.id.txt_movie_overview);
        }

        void bindItem(Movie movie) {
            String urlPoster = BuildConfig.IMAGE_URL + movie.getMoviePoster();
            Glide.with(itemView.getContext())
                    .load(urlPoster)
                    .apply(new RequestOptions().override(350, 550))
                    .placeholder(R.drawable.item_glide_placeholder)
                    .error(R.drawable.item_glide_error)
                    .transform(new RoundedCorners(32))
                    .into(imgMoviePoster);

            txtMovieTitle.setText(movie.getMovieTitle());
            txtMovieRelease.setText(FormatDate.getFormatReleaseDate(movie.getMovieRelease()));
            txtMovieScore.setText(String.valueOf(movie.getMovieScore()));
            txtMovieOverview.setText(movie.getMovieOverview());
            itemView.setOnClickListener(getItemClickListener(movie));
        }

        private View.OnClickListener getItemClickListener(final Movie movie){
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(movie);
                }
            };
        }
    }
}
