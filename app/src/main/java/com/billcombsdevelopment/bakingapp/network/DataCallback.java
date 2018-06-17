/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.network;

import com.billcombsdevelopment.bakingapp.model.Recipe;

import java.util.List;

public interface DataCallback {
    void onSuccess(List<Recipe> recipeList);
    void onFailure(String message);
}
