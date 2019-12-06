package com.informatika.umm.myapplication.ui.tvshows;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.TvShows;

public class TvShowsDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOWS = "extra_tvshows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ImageView imgTvShowsBackdrop, imgTvShowsPoster;
        final TextView txtTvShowsTitle, txtTvShowsRelease, txtTvShowsScore, txtTvShowsOverview;
        final RatingBar ratingBar;

        setContentView(R.layout.activity_detail_tv_shows);
        ratingBar = findViewById(R.id.rate_tvshows);
        imgTvShowsPoster = findViewById(R.id.img_tv_shows_poster);
        imgTvShowsBackdrop = findViewById(R.id.img_tv_shows_backdrop);
        txtTvShowsTitle = findViewById(R.id.txt_tv_shows_title);
        txtTvShowsRelease = findViewById(R.id.txt_tv_shows_release_date);
        txtTvShowsScore = findViewById(R.id.txt_tv_shows_score);
        txtTvShowsOverview = findViewById(R.id.txt_tv_shows_overview);

        final AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setVisibility(View.INVISIBLE);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_black_24dp);
            getSupportActionBar().setTitle(" ");
        }

        ratingBar.setVisibility(View.INVISIBLE);

        final ShimmerFrameLayout shimmerFrameLayout = findViewById(R.id.shimmer_container);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TvShows tvShows = getIntent().getParcelableExtra(EXTRA_TVSHOWS);
                        if (tvShows != null) {
                            appBarLayout.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);

                            String urlPoster = BuildConfig.IMAGE_URL + tvShows.getTvShowsPoster();
                            String urlBackdrop = BuildConfig.IMAGE_URL + tvShows.getTvShowsBackdrop();

                            Glide.with(TvShowsDetailActivity.this)
                                    .load(urlPoster)
                                    .override(350, 550)
                                    .transform(new RoundedCorners(32))
                                    .into(imgTvShowsPoster);

                            Glide.with(TvShowsDetailActivity.this)
                                    .load(urlBackdrop)
                                    .override(1366, 768)
                                    .into(imgTvShowsBackdrop);

                            ratingBar.setVisibility(View.VISIBLE);
                            if (tvShows.getTvShowsScore() >= 1 && tvShows.getTvShowsScore() < 2.0) {
                                ratingBar.setRating(1.0f);
                            } else if (tvShows.getTvShowsScore() >= 2.0 && tvShows.getTvShowsScore() < 5.0) {
                                ratingBar.setRating(2.0f);
                            } else if (tvShows.getTvShowsScore() >= 5.0 && tvShows.getTvShowsScore() < 6.0) {
                                ratingBar.setRating(2.5f);
                            } else if (tvShows.getTvShowsScore() >= 6.0 && tvShows.getTvShowsScore() < 7.0) {
                                ratingBar.setRating(3.0f);
                            } else if (tvShows.getTvShowsScore() >= 7.0 && tvShows.getTvShowsScore() < 8.0) {
                                ratingBar.setRating(3.5f);
                            } else if (tvShows.getTvShowsScore() >= 8.0 && tvShows.getTvShowsScore() < 9.0) {
                                ratingBar.setRating(4.0f);
                            } else if (tvShows.getTvShowsScore() >= 9.0) {
                                ratingBar.setRating(4.5f);
                            } else if (tvShows.getTvShowsScore() >= 9.5) {
                                ratingBar.setRating(5.0f);
                            } else {
                                ratingBar.setRating(0);
                            }

                            String tvShowsScore = Double.toString(tvShows.getTvShowsScore());
                            txtTvShowsScore.setText(tvShowsScore);

                            txtTvShowsTitle.setText(tvShows.getTvshowsTitles());
                            txtTvShowsRelease.setText(tvShows.getTvShowsRelease());
                            txtTvShowsOverview.setText(tvShows.getTvShowsOverview());

                            shimmerFrameLayout.setVisibility(View.INVISIBLE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
