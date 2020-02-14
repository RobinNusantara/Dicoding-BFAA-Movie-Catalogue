package com.informatika.umm.consumerapp.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.informatika.umm.consumerapp.R;
import com.informatika.umm.consumerapp.ui.fragment.movie.MovieFragment;
import com.informatika.umm.consumerapp.ui.fragment.tvshow.TvShowFragment;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/14/2020 02 2020
 * 13:51 Fri
 **/
public class PagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    public PagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    private final int[] TAB_TITLE = new int[]{
            R.string.str_movie,
            R.string.str_show
    };

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new TvShowFragment();
                break;
        }
        return fragment;
    }

    public CharSequence getPageTitle(int position){
        return context.getResources().getString(TAB_TITLE[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
