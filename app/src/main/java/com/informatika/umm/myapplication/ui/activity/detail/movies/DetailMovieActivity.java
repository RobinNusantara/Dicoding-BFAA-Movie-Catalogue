package com.informatika.umm.myapplication.ui.activity.detail.movies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
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
import com.informatika.umm.myapplication.widget.ImageBannerWidget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView txtMovieTitle, txtMovieRelease, txtMovieScore, txtMovieRuntime, txtMovieStatus, txtMovieOverview, txtMovieReview;
    private RecyclerView rvSimilarMovies, rvGenreMovies;
    private ImageView imgMovieBackdrop, imgMoviePoster;
    private List<Movie> movieList = new ArrayList<>();
    private List<Genre> genreList = new ArrayList<>();
    private FloatingActionButton btnCheckFavorite;
    private InsertFavoriteTask insertFavoriteTask;
    private RemoveFavoriteTask removeFavoriteTask;
    private DetailMovieViewModel viewModel;
    private MovieCardAdapter cardAdapter;
    private GenreAdapter genreAdapter;
    private RatingBar ratingBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        Movie movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
        bindView();
        setupToolbar();
        setupViewModel();
        if (movies != null) {
            getDetailMovie(movies);
            viewModel.loadDetailMovie(movies);
            viewModel.loadGenreMovie(movies);
            viewModel.loadSimilarMovie(movies);
        }
        setupRecyclerGenreMovie();
        setupRecyclerSimilarMovie();
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
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailMovieViewModel.class);
        viewModel.getDetailMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                showDetailMovie(movie);
                initFavoriteTask(movie);
                checkFavoriteMovie(movie);
                handleFavoriteIcon(movie.getIsFavorite());
            }
        });

        viewModel.getGenreMovie().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genreList) {
                genreAdapter.setGenre(genreList);
            }
        });

        viewModel.getSimilarMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                cardAdapter.setMovie(movieList);
            }
        });
    }

    private void showDetailMovie(final Movie movie) {
        txtMovieRuntime.setText(FormatRuntime.getFormatRuntimeMovie(DetailMovieActivity.this, movie.getMovieRuntime()));
        txtMovieStatus.setText(movie.getMovieStatus());
        btnCheckFavorite.setOnClickListener(getFavoriteListener());
    }

    private void getDetailMovie(Movie movie) {
        String urlPoster = BuildConfig.IMAGE_URL + movie.getMoviePoster();
        String urlBackdrop = BuildConfig.IMAGE_URL + movie.getMovieBackdrop();

        Glide.with(DetailMovieActivity.this)
                .load(urlPoster)
                .override(350, 550)
                .transform(new RoundedCorners(32))
                .placeholder(R.drawable.item_glide_placeholder)
                .error(R.drawable.item_glide_error)
                .into(imgMoviePoster);

        Glide.with(DetailMovieActivity.this)
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

    private void setupRecyclerSimilarMovie() {
        cardAdapter = new MovieCardAdapter(movieList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarMovies.setHasFixedSize(true);
        rvSimilarMovies.setLayoutManager(layoutManager);
        rvSimilarMovies.setAdapter(cardAdapter);
    }

    private void setupRecyclerGenreMovie() {
        genreAdapter = new GenreAdapter(genreList);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvGenreMovies.setHasFixedSize(true);
        rvGenreMovies.setLayoutManager(manager);
        rvGenreMovies.setAdapter(genreAdapter);
    }

    private void initFavoriteTask(Movie movie) {
        int id = Objects.requireNonNull(viewModel.getDetailMovie().getValue()).getMovieId();
        removeFavoriteTask = new RemoveFavoriteTask(this, id);
        insertFavoriteTask = new InsertFavoriteTask(this);
        movie.setType(getString(R.string.str_key_movies));
    }

    private void checkFavoriteMovie(Movie movie) {
        new GetFavoriteTask(this, movie.getMovieId()).execute();
    }

    private View.OnClickListener getFavoriteListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isFavorite = Objects.requireNonNull(viewModel.getDetailMovie().getValue()).getIsFavorite();
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
        btnCheckFavorite.setImageResource(isFavorite ?  R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp );
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateWidget() {
        new ImageBannerWidget().updateWidgetComponent(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
        private WeakReference<DetailMovieActivity> activity;

        GetFavoriteTask(DetailMovieActivity activity, int id) {
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

        private WeakReference<DetailMovieActivity> activity;

        InsertFavoriteTask(DetailMovieActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Movie movie = activity.get().viewModel.getDetailMovie().getValue();
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
            new ImageBannerWidget().updateWidgetComponent(activity.get().getApplicationContext());
        }
    }

    private static class RemoveFavoriteTask extends AsyncTask<Void, Void, Void> {

        private int id;
        private WeakReference<DetailMovieActivity> activity;

        RemoveFavoriteTask(DetailMovieActivity activity, int id) {
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
            activity.get().updateWidget();
        }
    }
}
