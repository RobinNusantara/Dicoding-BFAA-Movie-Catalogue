package com.informatika.umm.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.informatika.umm.myapplication.model.TvShows;


public class DetailTvShowsActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOWS = "extra_tvshows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imgTvShowsBackdrop, imgTvShowsPoster;
        TextView txtTvShowsTitle, txtTvShowsRelease, txtTvShowsScore, txtTvShowsRuntime, txtTvShowsOverview;
        TvShows tvShows = getIntent().getParcelableExtra(EXTRA_TVSHOWS);

        setContentView(R.layout.activity_detail_tv_shows);
        imgTvShowsBackdrop = findViewById(R.id.img_tv_shows_backdrop);
        imgTvShowsPoster = findViewById(R.id.img_tv_shows_poster);
        txtTvShowsTitle = findViewById(R.id.txt_tv_shows_title);
        txtTvShowsRelease = findViewById(R.id.txt_tv_shows_release_date);
        txtTvShowsScore = findViewById(R.id.txt_tv_shows_score);
        txtTvShowsRuntime = findViewById(R.id.txt_tvshows_runtime);
        txtTvShowsOverview = findViewById(R.id.txt_tv_shows_overview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(" ");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_black_24dp);
        }

        if (tvShows != null) {

            Glide.with(this)
                    .load(tvShows.getTvShowsPoster())
                    .override(350, 550)
                    .transform(new RoundedCorners(32))
                    .into(imgTvShowsPoster);

            Glide.with(this)
                    .load(tvShows.getTvShowsBackdrop())
                    .override(1366, 768)
                    .into(imgTvShowsBackdrop);


            txtTvShowsTitle.setText(tvShows.getTvshowsTitles());
            txtTvShowsRelease.setText(tvShows.getTvShowsRelease());
            txtTvShowsScore.setText(tvShows.getTvShowsScore());
            txtTvShowsRuntime.setText(tvShows.getTvShowsRuntime());
            txtTvShowsOverview.setText(tvShows.getTvShowsOverview());

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
