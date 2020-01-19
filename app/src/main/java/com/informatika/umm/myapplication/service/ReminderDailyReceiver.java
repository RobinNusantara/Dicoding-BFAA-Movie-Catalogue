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
import com.informatika.umm.myapplication.ui.activity.main.MainActivity;

import java.util.Calendar;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/18/2020 01 2020
 * 16:41 Sat
 **/
public class ReminderDailyReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_01";
    private static final CharSequence CHANNEL_NAME = "daily_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context,
                context.getString(R.string.app_name),
                context.getString(R.string.str_daily_message)
        );
    }

    private static PendingIntent getDailyReminder(Context context) {
        Intent intent = new Intent(context, ReminderDailyReceiver.class);
        boolean isActive = (PendingIntent.getBroadcast(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null
        );
        Log.d("dailyReminderActive", "" + isActive);
        return PendingIntent.getBroadcast(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
    }

    private void sendNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                ReminderDailyReceiver.NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(false)
                .setSound(ringtone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private Calendar setReminderTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public void setTimeDailyReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    setReminderTime().getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    getDailyReminder(context)
            );
        }
    }

    public void cancelDailyReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getDailyReminder(context));
        }
    }
}
