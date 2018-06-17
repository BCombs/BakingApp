/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.network;

import com.billcombsdevelopment.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApi {
    @GET("baking.json")
    Call<List<Recipe>> getJson();
}
