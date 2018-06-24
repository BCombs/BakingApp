/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.billcombsdevelopment.bakingapp.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Requests {

    public Requests() {
    }

    public void getJson(final DataCallback callback) {
        Retrofit client = RetrofitClient.getInstance();
        RecipeApi recipeApi = client.create(RecipeApi.class);

        Call<ArrayList<Recipe>> call = recipeApi.getJson();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Recipe>> call, @NonNull Response<ArrayList<Recipe>> response) {
                ArrayList<Recipe> recipes = response.body();
                callback.onSuccess(recipes);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Recipe>> call, @NonNull Throwable t) {
                Log.d("onFailure", t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }
}
