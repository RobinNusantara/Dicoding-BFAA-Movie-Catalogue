package com.informatika.umm.myapplication.settings;


import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.service.ReminderDailyReceiver;
import com.informatika.umm.myapplication.service.ReminderReleaseReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    private static final String KEY_DAILY = "reminder_daily";
    private static final String KEY_RELEASE = "reminder_release";
    private ReminderDailyReceiver dailyReceiver;
    private ReminderReleaseReceiver releaseReceiver;
    private NotificationViewModel viewModel;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        viewModel.loadReleaseMovie();
        dailyReceiver = new ReminderDailyReceiver();
        releaseReceiver = new ReminderReleaseReceiver();

        SwitchPreferenceCompat switchDaily = findPreference(KEY_DAILY);
        SwitchPreferenceCompat switchRelease = findPreference(KEY_RELEASE);
        if (switchDaily != null) {
            switchDaily.setOnPreferenceChangeListener(this);
        }

        if (switchRelease != null) {
            switchRelease.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.reminder_preferences, rootKey);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String keyPreference = preference.getKey();
        boolean isActive = (boolean) newValue;

        switch (keyPreference) {
            case KEY_DAILY:
                if (isActive) {
                    if (getActivity() != null) {
                        dailyReceiver.setTimeDailyReminder(getActivity());
                        Toast.makeText(getContext(), "Enable Daily Reminder", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (getActivity() != null) {
                        dailyReceiver.cancelDailyReminder(getActivity());
                        Toast.makeText(getContext(), "Disable Daily Reminder", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            case KEY_RELEASE:
                if (isActive) {
                    if (getActivity() != null) {
                        releaseReceiver.setTimeReleaseReminder(getActivity(), movieList);
                        Toast.makeText(getContext(), "Enable Release Reminder", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (getActivity() != null) {
                        releaseReceiver.cancelReleaseReminder(getActivity());
                        Toast.makeText(getContext(), "Disable Release Reminder", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
        }
        return false;
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(NotificationViewModel.class);
        viewModel.getReleaseMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movie) {
                movieList.clear();
                movieList.addAll(movie);
            }
        });
    }
}
