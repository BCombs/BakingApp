/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Ingredient;
import com.billcombsdevelopment.bakingapp.model.Recipe;
import com.billcombsdevelopment.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    @BindView(R.id.recipe_name_tv)
    TextView mRecipeTextView;

    Recipe mRecipe;
    List<Ingredient> mIngredients;
    List<Step> mSteps;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this, view);

        if (getArguments().containsKey("recipe")) {
            mRecipe = getArguments().getParcelable("recipe");
            mIngredients = mRecipe.getIngredients();
            mSteps = mRecipe.getSteps();
        }

        mRecipeTextView.setText(mRecipe.getName());


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
