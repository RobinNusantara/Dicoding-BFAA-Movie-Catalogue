package com.informatika.umm.myapplication.ui.activity.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.GenreAdapter;
import com.informatika.umm.myapplication.adapter.MovieCardAdapter;
import com.informatika.umm.myapplication.api.Client;
import com.informatika.umm.myapplication.api.Service;
import com.informatika.umm.myapplication.database.FavoriteHelper;
import com.informatika.umm.myapplication.model.Genre;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.ui.fragment.tvshows.TvShowsViewModel;
import com.informatika.umm.myapplication.util.FormatDate;
import com.informatika.umm.myapplication.util.FormatRuntime;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieRuntime, txtMovieStatus, txtMovieOverview, txtMovieSimilar;
    ImageView imgMovieBackdrop, imgMoviePoster;
    RatingBar ratingBar;
    GenreAdapter genreAdapter;
    RecyclerView rvGenreMovies, rvSimilarMovies;
    Movie movies;
    MovieCardAdapter cardAdapter;
    FavoriteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        bindView();
        setupToolbar();

        movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movies != null) {
            loadMovieDetail(movies.getMovieId());
        }

        helper = FavoriteHelper.getInstance(getApplicationContext());
        helper.open();

        List<Movie> movieList = new ArrayList<>();
        cardAdapter = new MovieCardAdapter(getApplicationContext(), movieList);
        rvSimilarMovies.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarMovies.setLayoutManager(layoutManager);
        rvSimilarMovies.setAdapter(cardAdapter);

        List<Genre> genreList = new ArrayList<>();
        genreAdapter = new GenreAdapter(getApplicationContext(), genreList);
        rvGenreMovies.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvGenreMovies.setLayoutManager(manager);
        rvGenreMovies.setAdapter(genreAdapter);

        TvShowsViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);
        viewModel.loadSimilarMovie(movies.getMovieId());
        viewModel.getMovie().observe(this, getMovie);
    }

    private Observer<List<Movie>> getMovie = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movie) {
            cardAdapter.setMovie(movie);
        }
    };

    private void bindView() {
        ratingBar = findViewById(R.id.rate_movie);
        imgMoviePoster = findViewById(R.id.img_movie_poster);
        imgMovieBackdrop = findViewById(R.id.img_movie_backdrop);
        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtMovieRelease = findViewById(R.id.txt_movie_release_date);
        txtMovieScore = findViewById(R.id.txt_movie_score);
        txtMovieRuntime = findViewById(R.id.txt_movie_runtime);
        txtMovieOverview = findViewById(R.id.txt_movie_overview);
        txtMovieSimilar = findViewById(R.id.txt_movie_similar);
        txtMovieStatus = findViewById(R.id.txt_movie_status);
        rvSimilarMovies = findViewById(R.id.rv_movies_similar);
        rvGenreMovies = findViewById(R.id.rv_genre);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    private void loadMovieDetail(int id) {
        Service apiService = Client.getClient().create(Service.class);
        Call<Movie> call = apiService.getMovieDetails("tv", id, BuildConfig.API_KEY, BuildConfig.LANGUAGE);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                List<Genre> genres = null;
                if (response.body() != null) {
                    genres = response.body().getMovieGenre();
                }
                if (response.isSuccessful()) {
                    Movie movies = response.body();
                    if (movies != null) {
                        String urlPoster = BuildConfig.IMAGE_URL + movies.getMoviePoster();
                        String urlBackdrop = BuildConfig.IMAGE_URL + movies.getMovieBackdrop();

                        Glide.with(TvShowsDetailActivity.this)
                                .load(urlPoster)
                                .override(350, 550)
                                .transform(new RoundedCorners(32))
                                .placeholder(R.drawable.glide_placeholder)
                                .error(R.drawable.glide_error)
                                .into(imgMoviePoster);

                        Glide.with(TvShowsDetailActivity.this)
                                .load(urlBackdrop)
                                .override(1366, 768)
                                .placeholder(R.drawable.glide_placeholder)
                                .error(R.drawable.glide_error)
                                .into(imgMovieBackdrop);

                        ratingBar.setRating(movies.getRating());
                        txtMovieScore.setText(String.valueOf(movies.getMovieScore()));
                        txtMovieTitle.setText(movies.getMovieTitle());
                        txtMovieRelease.setText(FormatDate.getFormatReleaseDate(movies.getMovieRelease()));
                        txtMovieOverview.setText(movies.getMovieOverview());
                        int episdeRunTime = (!movies.getEpisodeRunTime().isEmpty()) ? movies.getEpisodeRunTime().get(0) : 0;
                        txtMovieRuntime.setText(FormatRuntime.getFormatRuntimeTv(TvShowsDetailActivity.this, episdeRunTime));
                        txtMovieSimilar.setText(movies.getMovieTitle());
                        txtMovieStatus.setText(movies.getMovieStatus());
                        genreAdapter.setGenre(genres);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "onFailure");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (helper.isExist(movies)) {
            getMenuInflater().inflate(R.menu.toolbar_menu_fav_full, menu);
        } else {
            getMenuInflater().inflate(R.menu.toolbar_menu_fav_border, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.btn_add_to_favorite) {
            if (!helper.isExist(movies)) {
                long result = helper.insert(movies);
                if (result > 0) {
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                    Toast.makeText(getApplicationContext(), R.string.str_add_to_favorite, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.str_add_to_favorite_fail, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.str_have_already, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (item.getItemId() == R.id.btn_remove_from_favorite) {
                int result = helper.delete(movies.getMovieId());
                if (result > 0) {
                    item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    Toast.makeText(getApplicationContext(), R.string.str_remove_from_favorite, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.str_remove_from_favorite_fail, Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
