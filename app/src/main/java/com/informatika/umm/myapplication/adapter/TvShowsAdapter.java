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
import com.informatika.umm.myapplication.ui.tvshows.TvShowsDetailActivity;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.TvShows;

import java.util.ArrayList;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 11/30/2019 11 2019
 * 18:31 Sat
 **/
public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder> {

    private Context context;
    private ArrayList<TvShows> listTvShows = new ArrayList<>();

    public TvShowsAdapter(Context context) {
        this.context = context;
    }

    public void setTvShows(ArrayList<TvShows> listTvShows) {
        this.listTvShows.clear();
        this.listTvShows.addAll(listTvShows);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_tvshows, viewGroup, false);
        return new TvShowsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowsViewHolder holder, int position) {

        final TvShows tvShows = listTvShows.get(position);
        String urlPoster = BuildConfig.IMAGE_URL + tvShows.getTvShowsPoster();
        Glide.with(holder.itemView.getContext())
                .load(urlPoster)
                .apply(new RequestOptions().override(350, 550))
                .transform(new RoundedCorners(32))
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_error)
                .into(holder.imgTvShowsPoster);

        holder.txtTvShowsTitle.setText(tvShows.getTvShowsTitles());
        holder.txtTvShowsRelease.setText(tvShows.getTvShowsRelease());

        if (tvShows.getTvShowsScore() >= 1.0 && tvShows.getTvShowsScore() < 2.0) {
            holder.ratingBar.setRating(1.0f);
        } else if (tvShows.getTvShowsScore() >= 2.0 && tvShows.getTvShowsScore() < 5.0) {
            holder.ratingBar.setRating(2.0f);
        } else if (tvShows.getTvShowsScore() >= 5.0 && tvShows.getTvShowsScore() < 6.0) {
            holder.ratingBar.setRating(2.5f);
        } else if (tvShows.getTvShowsScore() >= 6.0 && tvShows.getTvShowsScore() < 7.0) {
            holder.ratingBar.setRating(3.0f);
        } else if (tvShows.getTvShowsScore() >= 7.0 && tvShows.getTvShowsScore() < 8.0) {
            holder.ratingBar.setRating(3.5f);
        } else if (tvShows.getTvShowsScore() >= 8.0 && tvShows.getTvShowsScore() < 9.0) {
            holder.ratingBar.setRating(4.0f);
        } else if (tvShows.getTvShowsScore() >= 9.0) {
            holder.ratingBar.setRating(4.5f);
        } else if (tvShows.getTvShowsScore() >= 9.5) {
            holder.ratingBar.setRating(5.0f);
        } else {
            holder.ratingBar.setRating(0);
        }

        String tvShowsScore = Double.toString(tvShows.getTvShowsScore());
        holder.txtTvShowsScore.setText(tvShowsScore);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(context, TvShowsDetailActivity.class);
                intent.putExtra(TvShowsDetailActivity.EXTRA_TVSHOWS, tvShows);
                context.startActivity(intent);
            }
        });

        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return listTvShows.size();
    }

    class TvShowsViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTvShowsPoster;
        TextView txtTvShowsTitle, txtTvShowsScore, txtTvShowsRelease;
        RatingBar ratingBar;

        TvShowsViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.rate_tvshows);
            imgTvShowsPoster = itemView.findViewById(R.id.img_tv_shows_poster);
            txtTvShowsTitle = itemView.findViewById(R.id.txt_tv_shows_title);
            txtTvShowsRelease = itemView.findViewById(R.id.txt_tv_shows_release_date);
            txtTvShowsScore = itemView.findViewById(R.id.txt_tv_shows_score);
        }
    }
}
