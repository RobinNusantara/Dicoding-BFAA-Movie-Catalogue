package com.informatika.umm.myapplication.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.settings.SettingsActivity;
import com.informatika.umm.myapplication.ui.fragment.favorites.FavoriteFragment;
import com.informatika.umm.myapplication.ui.fragment.movies.MovieFragment;
import com.informatika.umm.myapplication.ui.fragment.tvshows.TvShowsFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_movies:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_tvshows:
                    fragment = new TvShowsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_favorite:
                    fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_movies);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.btn_notification:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_language:
                intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
