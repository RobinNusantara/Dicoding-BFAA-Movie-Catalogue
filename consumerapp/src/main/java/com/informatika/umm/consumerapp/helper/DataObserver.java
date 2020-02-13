package com.informatika.umm.consumerapp.helper;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.informatika.umm.consumerapp.task.GetFavoriteTask;
import com.informatika.umm.consumerapp.util.FavoriteListener;

import java.lang.ref.WeakReference;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/13/2020 02 2020
 * 17:40 Thu
 **/
public class DataObserver {
    public static class Observer extends ContentObserver {

        private WeakReference<Context> contextWeakReference;

        public Observer(Handler handler, Context context) {
            super(handler);
            contextWeakReference = new WeakReference<>(context);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new GetFavoriteTask(contextWeakReference.get(), (FavoriteListener) contextWeakReference.get()).execute();
        }
    }
}
