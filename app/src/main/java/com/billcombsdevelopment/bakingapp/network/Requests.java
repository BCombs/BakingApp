/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.billcombsdevelopment.bakingapp.model.Recipe;

import java.util.List;

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

        Call<List<Recipe>> call = recipeApi.getJson();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                callback.onSuccess(recipes);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.d("onFailure", t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }
}
