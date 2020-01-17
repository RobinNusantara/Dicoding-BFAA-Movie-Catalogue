package com.informatika.umm.myapplication.view.activity.detail.tvshows;

import android.os.Bundle;
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
import com.informatika.umm.myapplication.database.FavoriteHelper;
import com.informatika.umm.myapplication.model.Genre;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.util.FormatDate;
import com.informatika.umm.myapplication.util.FormatRuntime;

import java.util.List;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";
    private TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieRuntime, txtMovieStatus, txtMovieOverview, txtMovieReview;
    private RecyclerView rvGenreMovies, rvSimilarMovies;
    private ImageView imgMovieBackdrop, imgMoviePoster;
    private DetailTvShowViewModel viewModel;
    private FavoriteHelper helper;
    private RatingBar ratingBar;
    private Toolbar toolbar;
    private Movie movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        movies = getIntent().getParcelableExtra(EXTRA_TV);
        bindView();
        setupToolbar();
        setupViewModel();
        getDetailTvShow(movies);
        viewModel.loadDetailTvShow(movies);
        viewModel.loadGenreTvShow(movies);
        viewModel.loadSimilarTvShow(movies);

        helper = FavoriteHelper.getInstance(getApplicationContext());
        helper.open();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailTvShowViewModel.class);
        viewModel.getDetailTvShow().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                showDetailTvShow(movie);
            }
        });

        viewModel.getGenreTvShow().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genreList) {
                showGenreTvShow(genreList);
            }
        });

        viewModel.getSimilarTvShow().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                showSimilarTvShow(movieList);
            }
        });
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        ratingBar = findViewById(R.id.rate_movie);
        imgMoviePoster = findViewById(R.id.img_movie_poster);
        imgMovieBackdrop = findViewById(R.id.img_movie_backdrop);
        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtMovieRelease = findViewById(R.id.txt_movie_release_date);
        txtMovieScore = findViewById(R.id.txt_movie_score);
        txtMovieRuntime = findViewById(R.id.txt_movie_runtime);
        txtMovieOverview = findViewById(R.id.txt_movie_overview);
        txtMovieStatus = findViewById(R.id.txt_movie_status);
        txtMovieReview = findViewById(R.id.txt_movie_vote);
        rvGenreMovies = findViewById(R.id.rv_genre);
        rvSimilarMovies = findViewById(R.id.rv_movies_similar);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    private void getDetailTvShow(Movie movie){
        String urlPoster = BuildConfig.IMAGE_URL + movie.getMoviePoster();
        String urlBackdrop = BuildConfig.IMAGE_URL + movie.getMovieBackdrop();

        Glide.with(DetailTvShowActivity.this)
                .load(urlPoster)
                .override(350, 550)
                .transform(new RoundedCorners(32))
                .placeholder(R.drawable.item_glide_placeholder)
                .error(R.drawable.item_glide_error)
                .into(imgMoviePoster);

        Glide.with(DetailTvShowActivity.this)
                .load(urlBackdrop)
                .override(1366, 768)
                .placeholder(R.drawable.item_glide_placeholder)
                .error(R.drawable.item_glide_error)
                .into(imgMovieBackdrop);

        txtMovieTitle.setText(movie.getMovieTitle());
        txtMovieRelease.setText(FormatDate.getFormatReleaseDate(movie.getMovieRelease()));
        ratingBar.setRating(movie.getRating());
        txtMovieScore.setText(String.valueOf(movie.getMovieScore()));
        txtMovieReview.setText(String.valueOf(movie.getMovieReview()));
        txtMovieOverview.setText(movie.getMovieOverview());
    }

    private void showDetailTvShow(Movie movie) {
        int episodeRunTime = (!movie.getEpisodeRunTime().isEmpty()) ? movie.getEpisodeRunTime().get(0) : 0;
        txtMovieRuntime.setText(FormatRuntime.getFormatRuntimeTv(DetailTvShowActivity.this, episodeRunTime));
        txtMovieStatus.setText(movie.getMovieStatus());
    }

    private void showSimilarTvShow(List<Movie> movieList) {
        MovieCardAdapter cardAdapter = new MovieCardAdapter(movieList);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarMovies.setHasFixedSize(true);
        rvSimilarMovies.setLayoutManager(manager);
        rvSimilarMovies.setAdapter(cardAdapter);
    }

    private void showGenreTvShow(List<Genre> genreList) {
        GenreAdapter genreAdapter = new GenreAdapter(getApplicationContext(), genreList);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvGenreMovies.setHasFixedSize(true);
        rvGenreMovies.setLayoutManager(manager);
        rvGenreMovies.setAdapter(genreAdapter);
    }

    private void addToFavorite(MenuItem item) {
        if (!helper.isTvShowExist(movies)) {
            long result = helper.insertTvShow(movies);
            if (result > 0) {
                item.setIcon(R.drawable.ic_favorite_white_24dp);
                Toast.makeText(getApplicationContext(), R.string.str_add_to_favorite, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.str_add_to_favorite_fail, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.str_have_already, Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromFavorite(MenuItem item) {
        if (item.getItemId() == R.id.btn_remove_from_favorite) {
            int result = helper.deleteTvShow(movies.getMovieId());
            if (result > 0) {
                item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                Toast.makeText(getApplicationContext(), R.string.str_remove_from_favorite, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.str_remove_from_favorite_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (helper.isTvShowExist(movies)) {
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
            addToFavorite(item);
        } else {
            removeFromFavorite(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
