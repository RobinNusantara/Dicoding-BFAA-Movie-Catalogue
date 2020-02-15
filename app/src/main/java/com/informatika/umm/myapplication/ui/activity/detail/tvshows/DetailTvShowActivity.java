package com.informatika.umm.myapplication.ui.activity.detail.tvshows;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.adapter.GenreAdapter;
import com.informatika.umm.myapplication.adapter.MovieCardAdapter;
import com.informatika.umm.myapplication.database.RoomClient;
import com.informatika.umm.myapplication.model.Genre;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.util.FormatDate;
import com.informatika.umm.myapplication.util.FormatRuntime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";
    private TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieRuntime, txtMovieStatus, txtMovieOverview, txtMovieReview;
    private RecyclerView rvGenreMovies, rvSimilarMovies;
    private ImageView imgMovieBackdrop, imgMoviePoster;
    private List<Movie> movieList = new ArrayList<>();
    private List<Genre> genreList = new ArrayList<>();
    private FloatingActionButton btnCheckFavorite;
    private InsertFavoriteTask insertFavoriteTask;
    private RemoveFavoriteTask removeFavoriteTask;
    private DetailTvShowViewModel viewModel;
    private MovieCardAdapter cardAdapter;
    private GenreAdapter genreAdapter;
    private RatingBar ratingBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        Movie movies = getIntent().getParcelableExtra(EXTRA_TV);
        bindView();
        setupToolbar();
        setupViewModel();
        if (movies != null) {
            getDetailTvShow(movies);
            viewModel.loadDetailTvShow(movies);
            viewModel.loadGenreTvShow(movies);
            viewModel.loadSimilarTvShow(movies);
        }
        setupRecyclerGenreTvShow();
        setupRecyclerSimilarTvShow();
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
        btnCheckFavorite = findViewById(R.id.btn_check_favorite);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailTvShowViewModel.class);
        viewModel.getDetailTvShow().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                showDetailTvShow(movie);
                initFavoriteTask(movie);
                checkFavoriteMovie(movie);
                handleFavoriteIcon(movie.getIsFavorite());
            }
        });

        viewModel.getGenreTvShow().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genreList) {
                genreAdapter.setGenre(genreList);
            }
        });

        viewModel.getSimilarTvShow().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                cardAdapter.setMovie(movieList);
            }
        });
    }

    private void getDetailTvShow(Movie movie) {
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
        btnCheckFavorite.setOnClickListener(getFavoriteListener());
    }

    private void setupRecyclerSimilarTvShow() {
        cardAdapter = new MovieCardAdapter(movieList);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarMovies.setHasFixedSize(true);
        rvSimilarMovies.setLayoutManager(manager);
        rvSimilarMovies.setAdapter(cardAdapter);
    }

    private void setupRecyclerGenreTvShow() {
        genreAdapter = new GenreAdapter(genreList);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvGenreMovies.setHasFixedSize(true);
        rvGenreMovies.setLayoutManager(manager);
        rvGenreMovies.setAdapter(genreAdapter);
    }

    private void initFavoriteTask(Movie movie) {
        int id = Objects.requireNonNull(viewModel.getDetailTvShow().getValue()).getMovieId();
        removeFavoriteTask = new RemoveFavoriteTask(this, id);
        insertFavoriteTask = new InsertFavoriteTask(this);
        movie.setType(getString(R.string.str_key_shows));
    }

    private void checkFavoriteMovie(Movie movie) {
        new GetFavoriteTask(this, movie.getMovieId()).execute();
    }

    private View.OnClickListener getFavoriteListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isFavorite = Objects.requireNonNull(viewModel.getDetailTvShow().getValue()).getIsFavorite();
                if (isFavorite != null) {
                    handleFavoriteClicked(isFavorite);
                }
            }
        };
    }

    private void handleFavoriteClicked(Boolean isFavorite) {
        viewModel.setIsFavorite(isFavorite);
        if (isFavorite) {
            removeFavoriteTask.execute();
        } else {
            insertFavoriteTask.execute();
        }
    }

    private void handleFavoriteIcon(Boolean isFavorite) {
        btnCheckFavorite.setImageResource(isFavorite ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private static class GetFavoriteTask extends AsyncTask<Void, Void, Movie> {

        private int id;
        private WeakReference<DetailTvShowActivity> activity;

        GetFavoriteTask(DetailTvShowActivity activity, int id) {
            this.activity = new WeakReference<>(activity);
            this.id = id;
        }

        @Override
        protected Movie doInBackground(Void... voids) {
            return RoomClient
                    .getInstance(activity.get())
                    .getMovieDatabase()
                    .getMovieDao()
                    .getMovie(id);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            if (movie != null) {
                activity.get().viewModel.setIsFavorite(movie.getIsFavorite());
            } else {
                activity.get().viewModel.setIsFavorite(false);
            }
        }
    }

    private static class InsertFavoriteTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<DetailTvShowActivity> activity;

        InsertFavoriteTask(DetailTvShowActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Movie movie = activity.get().viewModel.getDetailTvShow().getValue();
            if (movie != null) {
                movie.setIsFavorite(true);
                RoomClient
                        .getInstance(activity.get())
                        .getMovieDatabase()
                        .getMovieDao()
                        .insertFavorite(movie);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.get().showMessage(activity.get().getString(R.string.str_add_to_favorite));
        }
    }

    private static class RemoveFavoriteTask extends AsyncTask<Void, Void, Void> {

        private int id;
        private WeakReference<DetailTvShowActivity> activity;

        RemoveFavoriteTask(DetailTvShowActivity activity, int id) {
            this.activity = new WeakReference<>(activity);
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            RoomClient.getInstance(activity.get())
                    .getMovieDatabase()
                    .getMovieDao()
                    .removeFavorite(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.get().showMessage(activity.get().getString(R.string.str_remove_from_favorite));
        }
    }
}
