/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Ingredient;
import com.billcombsdevelopment.bakingapp.model.Recipe;
import com.billcombsdevelopment.bakingapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe mRecipe;
    private List<Ingredient> mIngredients = new ArrayList<>();

    public RecipeRemoteViewsFactory(Context context, Intent intent) {
        Log.d("RemoteViewsFactory", "Constructor called.");
        mContext = context;
        Bundle bundle = intent.getBundleExtra("extras");
        mRecipe = bundle.getParcelable("recipe");
    }

    private void getIngredients() {
        mIngredients = mRecipe.getIngredients();
    }

    @Override
    public void onCreate() {
        getIngredients();
    }

    @Override
    public void onDataSetChanged() {
        getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("RecipeRemoteViewFactory", "getViewAt() called!");
        Ingredient ingredient = mIngredients.get(position);
        String ingredientName = Utils.toUpperCase(ingredient.getIngredientName());
        String amount = ingredient.getQuantity() + " " + ingredient.getMeasurement();
        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(),
                R.layout.recipe_widget_list_item);
        remoteView.setTextViewText(R.id.widget_ingredient_tv, ingredientName);
        remoteView.setTextViewText(R.id.widget_quantity_tv, amount);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
