/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Recipe;
import com.billcombsdevelopment.bakingapp.ui.MainActivity;

/**
 * Implementation of Recipe Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Recipe recipe) {
        Log.d("RecipeWidgetProvider", "updateRecipeWidget() called");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        if (recipe != null) {
            // A recipe has been selected
            views.setTextViewText(R.id.widget_title_tv, recipe.getName());

            // Set the remote adapter
            Intent listViewIntent = new Intent(context, ListViewService.class);
            Bundle extras = new Bundle();
            extras.putParcelable("recipe", recipe);
            listViewIntent.putExtra("extras", extras);
            views.setRemoteAdapter(R.id.widget_lv, listViewIntent);
        } else {
            views.setTextViewText(R.id.widget_title_tv,
                    context.getResources().getString(R.string.no_recipe_selected));
        }

        // Create pending intent
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Clicking on the widget will open the app
        views.setOnClickPendingIntent(R.id.widget_linear_layout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateRecipeWidget(context, appWidgetManager, appWidgetId, null);
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

