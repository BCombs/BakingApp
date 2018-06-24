/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.network;

import com.billcombsdevelopment.bakingapp.model.Recipe;

import java.util.ArrayList;

public interface DataCallback {
    void onSuccess(ArrayList<Recipe> recipeList);

    void onFailure(String message);
}
