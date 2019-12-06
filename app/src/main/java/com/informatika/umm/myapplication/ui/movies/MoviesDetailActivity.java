package com.informatika.umm.myapplication.ui.movies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movies;

public class MoviesDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ImageView imgMovieBackdrop, imgMoviePoster;
        final TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieOverview;
        final RatingBar ratingBar;

        setContentView(R.layout.activity_detail_movies);
        ratingBar = findViewById(R.id.rate_movie);
        imgMoviePoster = findViewById(R.id.img_movie_poster);
        imgMovieBackdrop = findViewById(R.id.img_movie_backdrop);
        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtMovieRelease = findViewById(R.id.txt_movie_release_date);
        txtMovieScore = findViewById(R.id.txt_movie_score);
        txtMovieOverview = findViewById(R.id.txt_movie_overview);

        final AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setVisibility(View.INVISIBLE);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_black_24dp);
            getSupportActionBar().setTitle("");
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
                        Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
                        if (movies != null) {
                            appBarLayout.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);

                            String urlPoster = BuildConfig.IMAGE_URL + movies.getMoviePoster();
                            String urlBackdrop = BuildConfig.IMAGE_URL + movies.getMovieBackdrop();

                            Glide.with(MoviesDetailActivity.this)
                                    .load(urlPoster)
                                    .override(350, 550)
                                    .transform(new RoundedCorners(32))
                                    .placeholder(R.drawable.ic_image_placeholder)
                                    .error(R.drawable.ic_image_error)
                                    .into(imgMoviePoster);

                            Glide.with(MoviesDetailActivity.this)
                                    .load(urlBackdrop)
                                    .override(1366, 768)
                                    .placeholder(R.color.colorClouds)
                                    .error(R.color.colorClouds)
                                    .into(imgMovieBackdrop);

                            ratingBar.setVisibility(View.VISIBLE);
                            if (movies.getMovieScore() >= 1.0 && movies.getMovieScore() < 2.0) {
                                ratingBar.setRating(1.0f);
                            } else if (movies.getMovieScore() >= 2.0 && movies.getMovieScore() < 5.0) {
                                ratingBar.setRating(2.0f);
                            } else if (movies.getMovieScore() >= 5.0 && movies.getMovieScore() < 6.0) {
                                ratingBar.setRating(2.5f);
                            } else if (movies.getMovieScore() >= 6.0 && movies.getMovieScore() < 7.0) {
                                ratingBar.setRating(3.0f);
                            } else if (movies.getMovieScore() >= 7.0 && movies.getMovieScore() < 8.0) {
                                ratingBar.setRating(3.5f);
                            } else if (movies.getMovieScore() >= 8.0 && movies.getMovieScore() < 9.0) {
                                ratingBar.setRating(4.0f);
                            } else if (movies.getMovieScore() >= 9.0) {
                                ratingBar.setRating(4.5f);
                            } else if (movies.getMovieScore() >= 9.5) {
                                ratingBar.setRating(5.0f);
                            } else {
                                ratingBar.setRating(0);
                            }

                            String txtMoviesScore = Double.toString(movies.getMovieScore());
                            txtMovieScore.setText(txtMoviesScore);

                            txtMovieTitle.setText(movies.getMovieTitle());
                            txtMovieRelease.setText(movies.getMovieRelease());
                            txtMovieOverview.setText(movies.getMovieOverview());

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
