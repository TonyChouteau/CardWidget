package fr.tonychouteau.cardwidget.manager;

import android.content.Context;
import android.widget.RemoteViews;

public class ContextManager {

    public Context context;
    public RemoteViews views;
    public int widgetId;

    public ContextManager(Context context, RemoteViews views, int widgetId) {
        this.context = context;
        this.views = views;
        this.widgetId = widgetId;
    }
}
