package com.informatika.umm.myapplication.ui.movies;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.MovieCardAdapter;
import com.informatika.umm.myapplication.api.Client;
import com.informatika.umm.myapplication.api.Service;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.model.MovieResponse;
import com.informatika.umm.myapplication.util.StringUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
   TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieRuntime, txtMovieGenre, txtMovieOverview, txtMovieSimilar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ImageView imgMovieBackdrop, imgMoviePoster;
        final RatingBar ratingBar;

        setContentView(R.layout.activity_detail_movies);
        ratingBar = findViewById(R.id.rate_movie);
        imgMoviePoster = findViewById(R.id.img_movie_poster);
        imgMovieBackdrop = findViewById(R.id.img_movie_backdrop);
        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtMovieRelease = findViewById(R.id.txt_movie_release_date);
        txtMovieScore = findViewById(R.id.txt_movie_score);
        txtMovieGenre = findViewById(R.id.txt_movie_genre);
        txtMovieRuntime = findViewById(R.id.txt_movie_runtime);
        txtMovieOverview = findViewById(R.id.txt_movie_overview);
        txtMovieSimilar = findViewById(R.id.txt_movie_similar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {

            String urlPoster = BuildConfig.IMAGE_URL + movie.getMoviePoster();
            String urlBackdrop = BuildConfig.IMAGE_URL + movie.getMovieBackdrop();

            Glide.with(MovieDetailActivity.this)
                    .load(urlPoster)
                    .override(350, 550)
                    .transform(new RoundedCorners(32))
                    .into(imgMoviePoster);

            Glide.with(MovieDetailActivity.this)
                    .load(urlBackdrop)
                    .override(1366, 768)
                    .placeholder(R.color.colorClouds)
                    .error(R.color.colorClouds)
                    .into(imgMovieBackdrop);

            ratingBar.setRating(movie.getRating());

            String txtMoviesScore = Double.toString(movie.getMovieScore());
            txtMovieScore.setText(txtMoviesScore);
            txtMovieTitle.setText(movie.getMovieTitle());
            txtMovieRelease.setText(movie.getMovieRelease());
            txtMovieOverview.setText(movie.getMovieOverview());
        }

        loadDetailsMovie();
        loadSimilarMovie();
    }

    private void loadSimilarMovie() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {
            Service apiService = Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getSimilarMovies(movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    ArrayList<Movie> movies = null;
                    if (response.body() != null) {
                        movies = response.body().getResults();
                    }
                    if (response.isSuccessful()) {
                        MovieCardAdapter cardAdapter = new MovieCardAdapter(getApplicationContext(), movies);
                        RecyclerView rvSimilarMovies = findViewById(R.id.rv_movies_similar);
                        rvSimilarMovies.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        rvSimilarMovies.setLayoutManager(layoutManager);
                        rvSimilarMovies.setAdapter(cardAdapter);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {

                }
            });
        }
    }

    private void loadDetailsMovie() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {

            Service apiService = Client.getClient().create(Service.class);
            Call<Movie> call = apiService.getMovieDetails(movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                    if (response.isSuccessful()) {
                        Movie movies = response.body();
                        if (movies != null) {
                            txtMovieGenre.setText(StringUtils.getGenre(movies.getMovieGenre()));
                            txtMovieRuntime.setText(StringUtils.getRuntime(MovieDetailActivity.this, movies.getMovieRuntime()));
                            txtMovieSimilar.setText(movies.getMovieTitle());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {

                }
            });
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
