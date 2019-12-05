package com.informatika.umm.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
        this.listTvShows = listTvShows;
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
        Glide.with(holder.itemView.getContext())
                .load(tvShows.getTvShowsPoster())
                .apply(new RequestOptions().override(350, 550))
                .transform(new RoundedCorners(32))
                .into(holder.imgTvShowsPoster);

        holder.txtTvShowsTitle.setText(tvShows.getTvshowsTitles());
        holder.txtTvShowsRelease.setText(tvShows.getTvShowsRelease());
        holder.txtTvShowsScore.setText(tvShows.getTvShowsScore());

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
        RelativeLayout container;

        TvShowsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            imgTvShowsPoster = itemView.findViewById(R.id.img_tv_shows_poster);
            txtTvShowsTitle = itemView.findViewById(R.id.txt_tv_shows_title);
            txtTvShowsRelease = itemView.findViewById(R.id.txt_tv_shows_release_date);
            txtTvShowsScore = itemView.findViewById(R.id.txt_tv_shows_score);
        }
    }
}
