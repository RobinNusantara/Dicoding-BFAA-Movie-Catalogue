package com.informatika.umm.myapplication.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.ui.activity.detail.movies.DetailMovieActivity;

import java.util.Calendar;
import java.util.List;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/18/2020 01 2020
 * 16:41 Sat
 **/
public class ReminderReleaseReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 110;
    private static final String CHANNEL_ID = "channel_02";
    private static final CharSequence CHANNEL_NAME = "release_channel";
    private static final String EXTRA_MOVIE = "extra_movie";
    private static final String KEY_MESSAGE_ID = "key_message_id";
    private static final String KEY_NOTIF_ID = "key_notify_id";
    private int messageId = 1100;

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(
                context,
                intent.getIntExtra(EXTRA_MOVIE, 0),
                intent.getStringExtra(KEY_MESSAGE_ID)
        );
    }

    private static PendingIntent getReleaseReminder(Context context) {
        Intent intent = new Intent(context, ReminderReleaseReceiver.class);
        boolean isActive = (PendingIntent.getBroadcast(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null
        );
        Log.d("releaseReminderActive", "" + isActive);
        return PendingIntent.getBroadcast(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
    }

    private void sendNotification(Context context, int id, String title) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, id);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(title + " has released today")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(false)
                .setSound(ringtone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }

    private Calendar setReminderTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public void setTimeReleaseReminder(Context context, List<Movie> movieList) {
        for (Movie movie : movieList) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReminderReleaseReceiver.class);
            intent.putExtra(EXTRA_MOVIE, movie.getMovieId());
            intent.putExtra(KEY_MESSAGE_ID, movie.getMovieTitle());
            intent.putExtra(KEY_NOTIF_ID, messageId);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    100,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            if (alarmManager != null) {
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        setReminderTime().getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
            }
        }
    }

    public void cancelReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getReleaseReminder(context));
        }
    }
}
