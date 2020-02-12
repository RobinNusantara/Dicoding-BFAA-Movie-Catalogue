package com.informatika.umm.myapplication.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.informatika.umm.myapplication.R;
import com.informatika.umm.myapplication.model.Movie;
import com.informatika.umm.myapplication.ui.activity.detail.movies.DetailMovieActivity;

/**
 * Implementation of App Widget functionality.
 */
public class ImageBannerWidget extends AppWidgetProvider {

    static final String EXTRA_MOVIE = "extra_movie";
    static final String KEY_WIDGET_ACTION = "key_widget action";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, WidgetRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.image_banner_widget);
        view.setRemoteAdapter(R.id.stack_view, intent);
        view.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent widgetIntent = new Intent(context, ImageBannerWidget.class);
        widgetIntent.setAction(KEY_WIDGET_ACTION);
        widgetIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                100,
                widgetIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        view.setPendingIntentTemplate(R.id.stack_view, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, view);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null && intent.getAction().equals(KEY_WIDGET_ACTION)) {
            Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);
            openMovieDetail(context, movie);
        }
        updateWidgetComponent(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void openMovieDetail(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void updateWidgetComponent(Context context) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, ImageBannerWidget.class);
        int[] id = widgetManager.getAppWidgetIds(componentName);
        if (id != null) {
            widgetManager.notifyAppWidgetViewDataChanged(id, R.id.stack_view);
        }
    }
}

