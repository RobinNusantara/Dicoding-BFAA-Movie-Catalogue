package com.informatika.umm.myapplication.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 2/7/2020 02 2020
 * 20:35 Fri
 **/
public class WidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }
}
