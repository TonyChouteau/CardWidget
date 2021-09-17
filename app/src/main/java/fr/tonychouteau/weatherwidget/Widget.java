package fr.tonychouteau.weatherwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.weatherwidget.R;

import fr.tonychouteau.weatherwidget.manager.ContextManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    private ContextManager contextManager;

    private static final String APPWIDGET_CLICK = "Click";

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        this.contextManager = new ContextManager(context, views, appWidgetId);
        this.initOnClickListener();

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public void initOnClickListener() {
        this.contextManager.views.setOnClickPendingIntent(
                R.id.widget_container,
                this.getPendingSelfIntent(this.contextManager.context, APPWIDGET_CLICK)
        );
    }

    PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, this.contextManager.widgetId, intent, 0);
    }

    public void displayVersion() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("h:mm:ss");
        this.contextManager.views.setTextViewText(R.id.date, format.format(currentTime));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        int[] appWidgetIDs = appWidgetManager
                .getAppWidgetIds(new ComponentName(context, Widget.class));

        for (int appWidgetId: appWidgetIDs) {
            if (APPWIDGET_CLICK.equals(intent.getAction())) {
                this.contextManager = new ContextManager(context, views, appWidgetId);
                this.displayVersion();
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
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
}