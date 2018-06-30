/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.billcombsdevelopment.bakingapp.model.Recipe;

public class RecipeWidgetUpdateService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE_WIDGETS =
            "com.billcombsdevelopment.bakingapp.action.update_recipe_widgets";
    private static String TAG = RecipeWidgetUpdateService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public RecipeWidgetUpdateService() {
        super(TAG);
    }

    public static void startActionUpdateRecipeWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeWidgetUpdateService.class);
        intent.putExtra("recipe", recipe);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(intent);
    }

    private void handleActionUpdateRecipeWidgets(Recipe recipe) {
        // Update the widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            RecipeWidgetProvider.updateRecipeWidget(this, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE_WIDGETS.equals(action)) {
                Recipe recipe = intent.getParcelableExtra("recipe");
                handleActionUpdateRecipeWidgets(recipe);
            }
        }
    }
}
