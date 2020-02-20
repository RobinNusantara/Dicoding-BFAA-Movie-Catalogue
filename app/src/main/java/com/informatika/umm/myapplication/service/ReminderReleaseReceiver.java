package com.informatika.umm.myapplication.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.informatika.umm.myapplication.BuildConfig;
import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.api.Client;
import com.informatika.umm.myapplication.api.Service;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.model.MovieResponse;
import com.informatika.umm.myapplication.ui.activity.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/18/2020 01 2020
 * 16:41 Sat
 **/
public class ReminderReleaseReceiver extends BroadcastReceiver {

    private static int NOTIFICATION_ID = 2;
    private static final int NOTIFICATION_REQUEST_CODE = 200;
    private static final String EXTRA_NOTIF_ID = "extra_notif_id";
    private static final String EXTRA_MOVIE = "extra_movie";
    private static final String CHANNEL_ID = "channel_02";
    private static final CharSequence CHANNEL_NAME = "release_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        getMovieRelease(context);
    }

    private void sendNotification(Context context, Movie movie, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(movie.getMovieTitle())
                .setContentText(movie.getMovieTitle() + context.getString(R.string.str_release_message))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(false)
                .setSound(ringtone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            builder.setChannelId(CHANNEL_ID);
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        if (notificationManager != null) {
            notificationManager.notify(id, builder.build());
        }
    }

    private void getMovieRelease(final Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String today = dateFormat.format(new Date());

        Service apiService = Client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getReleaseMovies(BuildConfig.API_KEY, today, today);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    filterMovieRelease(context, response.body().getResults(), today);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d(t.getMessage(), "onFailure");
            }
        });
    }

    private void filterMovieRelease(Context context, List<Movie> movies, String date) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMovieRelease() != null && movie.getMovieRelease().equals(date)) {
                filteredMovies.add(movie);
            }
        }
        setMovieNotification(context, filteredMovies);
    }

    public void setMovieNotification(final Context context, final List<Movie> movies) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = NOTIFICATION_ID;
                for (Movie movie : movies) {
                    sendNotification(context, movie, id);
                    id++;
                }
            }
        }).start();
    }

    private static PendingIntent getReleaseReminder(Context context) {
        Intent intent = new Intent(context, ReminderReleaseReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private Calendar setReminderTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public void setTimeReleaseReminder(Context context) {
        Intent intent = new Intent(context, ReminderReleaseReceiver.class);
        intent.putExtra(EXTRA_NOTIF_ID, NOTIFICATION_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    setReminderTime().getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
        NOTIFICATION_ID += 2;
    }

    public void cancelReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getReleaseReminder(context));
        }
    }
}
