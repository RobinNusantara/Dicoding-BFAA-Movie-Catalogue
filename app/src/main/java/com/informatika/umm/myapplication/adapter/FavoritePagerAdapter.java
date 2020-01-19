package com.informatika.umm.myapplication.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.ui.fragment.favorites.FavoriteFragmentMovie;
import com.informatika.umm.myapplication.ui.fragment.favorites.FavoriteFragmentTvShow;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/1/2020 01 2020
 * 15:18 Wed
 **/
public class FavoritePagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    public FavoritePagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.str_movies,
            R.string.str_tv_shows
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteFragmentMovie();
                break;
            case 1:
                fragment = new FavoriteFragmentTvShow();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
