package com.informatika.umm.myapplication.ui.movies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movies;

public class DetailMoviesActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imgMovieBackdrop, imgMoviePoster;
        TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieRuntime, txtMovieOverview;
        Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIE);

        setContentView(R.layout.activity_detail_movies);
        imgMoviePoster = findViewById(R.id.img_movie_poster);
        imgMovieBackdrop = findViewById(R.id.img_movie_backdrop);
        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtMovieRelease = findViewById(R.id.txt_movie_release_date);
        txtMovieScore = findViewById(R.id.txt_movie_score);
        txtMovieRuntime = findViewById(R.id.txt_movie_runtime);
        txtMovieOverview = findViewById(R.id.txt_movie_overview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_black_24dp);
            getSupportActionBar().setTitle("");
        }

        if (movies != null) {

            Glide.with(this)
                    .load(movies.getMoviePoster())
                    .override(350, 550)
                    .transform(new RoundedCorners(32))
                    .into(imgMoviePoster);

            Glide.with(this)
                    .load(movies.getMovieBackdrop())
                    .override(1366, 768)
                    .into(imgMovieBackdrop);

            txtMovieTitle.setText(movies.getMovieTitle());
            txtMovieRelease.setText(movies.getMovieRelease());
            txtMovieScore.setText(movies.getMovieScore());
            txtMovieRuntime.setText(movies.getMovieRuntime());
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
