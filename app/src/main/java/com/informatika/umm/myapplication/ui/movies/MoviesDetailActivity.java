package com.informatika.umm.myapplication.ui.movies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movies;

public class MoviesDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imgMovieBackdrop, imgMoviePoster;
        TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieOverview;
        RatingBar ratingBar;
        Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIE);

        setContentView(R.layout.activity_detail_movies);
        ratingBar = findViewById(R.id.rate_movie);
        imgMoviePoster = findViewById(R.id.img_movie_poster);
        imgMovieBackdrop = findViewById(R.id.img_movie_backdrop);
        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtMovieOverview = findViewById(R.id.txt_movie_overview);
        txtMovieRelease = findViewById(R.id.txt_movie_release_date);
        txtMovieScore = findViewById(R.id.txt_movie_score);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_black_24dp);
            getSupportActionBar().setTitle("");
        }

        if (movies != null) {

            String urlPoster = BuildConfig.IMAGE_URL + movies.getMoviePoster();
            String urlBackdrop = BuildConfig.IMAGE_URL + movies.getMovieBackdrop();

            Glide.with(this)
                    .load(urlPoster)
                    .override(350, 550)
                    .transform(new RoundedCorners(32))
                    .into(imgMoviePoster);

            Glide.with(this)
                    .load(urlBackdrop)
                    .override(1366, 768)
                    .into(imgMovieBackdrop);

            if (movies.getMovieScore() >= 1 && movies.getMovieScore() < 2.0) {
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
