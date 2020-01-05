package com.informatika.umm.myapplication.ui.movies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.GenreAdapter;
import com.informatika.umm.myapplication.adapter.MovieCardAdapter;
import com.informatika.umm.myapplication.api.Client;
import com.informatika.umm.myapplication.api.Service;
import com.informatika.umm.myapplication.model.Genre;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.model.MovieResponse;
import com.informatika.umm.myapplication.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieRuntime, txtMovieSimilar;
    ReadMoreTextView txtMovieOverview;
    ImageView imgMovieBackdrop, imgMoviePoster;
    RatingBar ratingBar;
    Movie movie;
    boolean isChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        ratingBar = findViewById(R.id.rate_movie);
        imgMoviePoster = findViewById(R.id.img_movie_poster);
        imgMovieBackdrop = findViewById(R.id.img_movie_backdrop);
        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtMovieRelease = findViewById(R.id.txt_movie_release_date);
        txtMovieScore = findViewById(R.id.txt_movie_score);
        txtMovieRuntime = findViewById(R.id.txt_movie_runtime);
        txtMovieOverview = findViewById(R.id.txt_movie_overview);
        txtMovieSimilar = findViewById(R.id.txt_movie_similar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {
            String urlPoster = BuildConfig.IMAGE_URL + movie.getMoviePoster();
            String urlBackdrop = BuildConfig.IMAGE_URL + movie.getMovieBackdrop();

            Glide.with(MovieDetailActivity.this)
                    .load(urlPoster)
                    .override(350, 550)
                    .transform(new RoundedCorners(32))
                    .placeholder(R.color.colorClouds)
                    .into(imgMoviePoster);

            Glide.with(MovieDetailActivity.this)
                    .load(urlBackdrop)
                    .override(1366, 768)
                    .placeholder(R.drawable.ic_movie_roll_logo)
                    .centerCrop()
                    .error(R.color.colorClouds)
                    .into(imgMovieBackdrop);
        }

        loadDetailsMovie();
        loadSimilarMovie();
    }

    private void loadDetailsMovie() {

        ArrayList<Genre> genreList = new ArrayList<>();
        final GenreAdapter genreAdapter = new GenreAdapter(getApplicationContext(), genreList);
        RecyclerView rvGenre = findViewById(R.id.rv_genre);
        rvGenre.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvGenre.setLayoutManager(layoutManager);
        rvGenre.setAdapter(genreAdapter);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {
            Service apiService = Client.getClient().create(Service.class);
            Call<Movie> call = apiService.getMovieDetails(movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                    ArrayList<Genre> genres = null;
                    if (response.body() != null) {
                        genres = response.body().getMovieGenre();
                    }
                    if (response.isSuccessful()) {
                        Movie movies = response.body();
                        if (movies != null) {
                            ratingBar.setRating(movies.getRating());
                            String txtMoviesScore = Double.toString(movies.getMovieScore());
                            txtMovieScore.setText(txtMoviesScore);
                            txtMovieTitle.setText(movies.getMovieTitle());
                            txtMovieRelease.setText(movies.getMovieRelease());
                            txtMovieOverview.setText(movies.getMovieOverview());
                            txtMovieRuntime.setText(Utils.getRuntime(MovieDetailActivity.this, movies.getMovieRuntime()));
                            txtMovieSimilar.setText(movies.getMovieTitle());
                            genreAdapter.setGenre(genres);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {

                }
            });
        }
    }
    private void loadSimilarMovie() {
        ArrayList<Movie> movieList = new ArrayList<>();
        final MovieCardAdapter cardAdapter = new MovieCardAdapter(getApplicationContext(), movieList);
        RecyclerView rvSimilarMovies = findViewById(R.id.rv_movies_similar);
        rvSimilarMovies.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarMovies.setLayoutManager(layoutManager);
        rvSimilarMovies.setAdapter(cardAdapter);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {
            Service apiService = Client.getClient().create(Service.class);
            final Call<MovieResponse> call = apiService.getSimilarMovies(movie.getMovieId(), BuildConfig.API_KEY, BuildConfig.LANGUAGE);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    ArrayList<Movie> movies = null;
                    if (response.body() != null) {
                        movies = response.body().getResults();
                    }
                    if (response.isSuccessful()) {
                        cardAdapter.setMovie(movies);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failed to Load Similar Movie", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.btn_favorite);
        checkable.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btn_favorite:
                isChecked = !item.isChecked();
                if (item.isChecked()) {
                    item.setChecked(isChecked);
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                    Toast.makeText(getApplicationContext(), R.string.str_add_to_favorite, Toast.LENGTH_SHORT).show();
                } else {
                    item.setChecked(isChecked);
                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(getApplicationContext(), R.string.str_remove_from_favorite, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
