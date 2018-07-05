/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Ingredient;
import com.billcombsdevelopment.bakingapp.model.Recipe;
import com.billcombsdevelopment.bakingapp.network.DataCallback;
import com.billcombsdevelopment.bakingapp.network.Requests;
import com.billcombsdevelopment.bakingapp.widget.RecipeWidgetUpdateService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataCallback, FragmentCommunicator {

    private ArrayList<Recipe> mRecipeList = null;
    private Recipe mCurrentRecipe = null;
    private boolean mTwoPane;
    @Nullable
    private BasicIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIdlingResource();

        // Initialize AppBar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Check if we are in two-pane
        mTwoPane = findViewById(R.id.tablet_linear_layout) != null;

        // If the back stack > 0 enable the home button
        setHomeButton();

        // Add on Change Listener
        getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        setHomeButton();
                    }
                });

        if (savedInstanceState != null) {
            // No need to download JSON data again
            if (savedInstanceState.containsKey("recipeList")) {
                mRecipeList = savedInstanceState.getParcelableArrayList("recipeList");
            }

            if (savedInstanceState.containsKey("currentRecipe")) {
                mCurrentRecipe = savedInstanceState.getParcelable("currentRecipe");
            }

            if (mTwoPane) {
                // If the secondary fragment container is null, we are viewing the recipe list
                // which needs to be full screen
                if (findViewById(R.id.secondary_fragment_container) == null) {
                    setSinglePane();
                }
            }
        } else {
            mIdlingResource.setIdleState(false);
            loadData();
        }
    }

    private void loadData() {
        Requests request = new Requests();
        request.getJson(this);
    }

    /**
     * When the user is viewing the RecipeListFragment (home ui) it will always be full screen
     * regardless of two-pane or not. If we are two-pane, we need to hide the
     * secondary fragment container
     */
    private void setHomeUi() {

        if (mTwoPane) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            setSinglePane();

            Bundle args = new Bundle();
            args.putParcelableArrayList("recipeList", mRecipeList);

            // Attach the recipe list fragment
            RecipeListFragment recipeListFragment = new RecipeListFragment();
            recipeListFragment.setArguments(args);
            transaction.replace(R.id.primary_fragment_container, recipeListFragment, "RecipeListFragment");
            transaction.commit();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            Bundle args = new Bundle();
            args.putParcelableArrayList("recipeList", mRecipeList);

            RecipeListFragment recipeListFragment = new RecipeListFragment();
            recipeListFragment.setArguments(args);
            transaction.replace(R.id.primary_fragment_container, recipeListFragment, "RecipeListFragment");
            transaction.commit();
        }

        // Data is loaded, set idle state to true
        mIdlingResource.setIdleState(true);
    }

    /**
     * Hides the secondary fragment container and divider making the primary fragment container
     * take up full screen
     */
    private void setSinglePane() {
        // Hide the secondary fragment container and divider
        FrameLayout secondaryFragmentContainer = findViewById(R.id.secondary_fragment_container);
        secondaryFragmentContainer.setVisibility(View.GONE);

        View divider = findViewById(R.id.divider);
        divider.setVisibility(View.GONE);
    }

    /**
     * Show the secondary fragment container and divider for master/detail
     */
    private void setTwoPane() {
        // Show the secondary fragment container and divider
        FrameLayout secondaryFragmentContainer = findViewById(R.id.secondary_fragment_container);
        secondaryFragmentContainer.setVisibility(View.VISIBLE);

        View divider = findViewById(R.id.divider);
        divider.setVisibility(View.VISIBLE);
    }

    /**
     * Checks if the back stack is empty or not. If it isn't, enable the Home button, if
     * it is, disable it.
     */
    private void setHomeButton() {
        // If the back stack isn't empty, enable the home button
        if (getSupportFragmentManager() != null &&
                getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            return;
        }

        // The back stack is empty. Disable the home button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipeList", mRecipeList);
        outState.putParcelable("currentRecipe", mCurrentRecipe);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            // If we are in two-pane mode the home button needs to return to
            // the full screen recipe list
            if (mTwoPane) {
                getSupportFragmentManager().popBackStackImmediate();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(getSupportFragmentManager()
                        .findFragmentById(R.id.secondary_fragment_container))
                        .commit();
                setHomeUi();
                return true;
            }

            getSupportFragmentManager().popBackStackImmediate();
            return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // If we are in two-pane and there is a fragment attached to the secondary container,
        // remove the fragment
        if (mTwoPane &&
                getSupportFragmentManager()
                        .findFragmentById(R.id.secondary_fragment_container) != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(getSupportFragmentManager()
                    .findFragmentById(R.id.secondary_fragment_container))
                    .commit();

            // Pop the list back stack to recipe menu and set to single pane
            getSupportFragmentManager().popBackStackImmediate();
            setSinglePane();
        }
    }

    /**
     * Sets the title of the app bar.
     *
     * @param title - Title displayed in app bar
     */
    public void setAppBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    // Callback for DataCallback
    @Override
    public void onSuccess(List<Recipe> recipeList) {
        mRecipeList = (ArrayList<Recipe>) recipeList;
        setHomeUi();
    }

    // Callback for DataCallback
    @Override
    public void onFailure(String message) {
        // Show the user the error message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Callback for when a user selects a recipe from the recipe list
     *
     * @param position - the position of the recipe in the list
     */
    @Override
    public void onRecipeSelected(int position) {

        // Update the currently selected recipe
        mCurrentRecipe = mRecipeList.get(position);

        // Update the widget
        startActionUpdateRecipeWidget();

        if (mTwoPane) {

            // Change the UI Layout
            setTwoPane();

            // Set the primary fragment container to the ingredient/step list
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            Bundle args = new Bundle();
            args.putParcelable("recipe", mCurrentRecipe);

            // Attach the recipe list fragment
            DetailListFragment detailListFragment = new DetailListFragment();
            detailListFragment.setArguments(args);
            transaction.replace(R.id.primary_fragment_container,
                    detailListFragment, "DetailListFragment");
            transaction.addToBackStack("RecipeListFragment");
            transaction.commit();

            // Attach the ingredient fragment
            args = new Bundle();
            args.putParcelableArrayList("ingredients", (ArrayList<Ingredient>) mCurrentRecipe.getIngredients());
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(args);

            // Begin a new transaction
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.secondary_fragment_container,
                    ingredientsFragment, "IngredientsFragment");
            transaction.commit();

        } else {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            Bundle args = new Bundle();
            args.putParcelable("recipe", mCurrentRecipe);

            // Attach the recipe list fragment
            DetailListFragment detailListFragment = new DetailListFragment();
            detailListFragment.setArguments(args);
            transaction.replace(R.id.primary_fragment_container, detailListFragment, "DetailListFragment");
            transaction.addToBackStack("RecipeListFragment");
            transaction.commit();
        }
    }

    /**
     * Update the widget
     */
    private void startActionUpdateRecipeWidget() {
        RecipeWidgetUpdateService.startActionUpdateRecipeWidgets(this, mCurrentRecipe);
    }

    /**
     * Callback for when the user clicks on the ingredients or a step in the
     * StepFragment
     *
     * @param position - position of item clicked on by user
     */
    @Override
    public void updateUi(int position) {

        FragmentManager fm = getSupportFragmentManager();

        switch (position) {

            case 0:
                // Open an ingredient fragment
                if (mTwoPane) {

                    // Create new ingredients fragment and set args to the
                    // ingredients of the currently selected recipe
                    IngredientsFragment ingredientsFragment = new IngredientsFragment();
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("ingredients",
                            (ArrayList<Ingredient>) mCurrentRecipe.getIngredients());
                    ingredientsFragment.setArguments(args);

                    // Replace the detail fragment
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.secondary_fragment_container, ingredientsFragment)
                            .commit();
                } else {

                    IngredientsFragment ingredientsFragment = new IngredientsFragment();
                    // Get the ingredients for the current recipe and add it as an argument
                    // of the fragment
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("ingredients",
                            (ArrayList<Ingredient>) mCurrentRecipe.getIngredients());
                    ingredientsFragment.setArguments(args);

                    // Replace the current fragment with IngredientsFragment
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.primary_fragment_container,
                            ingredientsFragment, "IngredientsFragment");
                    transaction.addToBackStack("DetailListFragment");
                    transaction.commit();
                }
                break;

            default:
                if (mTwoPane) {

                    // Create new step fragment and set args to the
                    // selected step of the current recipe
                    StepFragment stepFragment = new StepFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("step",
                            mCurrentRecipe.getSteps().get(position - 1));
                    stepFragment.setArguments(args);

                    // Replace the detail fragment
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.secondary_fragment_container, stepFragment)
                            .commit();
                } else {

                    StepFragment stepFragment = new StepFragment();
                    // Get the ingredients for the current recipe and add it as an argument
                    // of the fragment
                    Bundle args = new Bundle();
                    args.putParcelable("step", mCurrentRecipe.getSteps().get(position - 1));
                    stepFragment.setArguments(args);

                    // Replace the current fragment with IngredientsFragment
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.primary_fragment_container,
                            stepFragment, "IngredientsFragment");
                    transaction.addToBackStack("DetailListFragment");
                    transaction.commit();
                }
        }
    }

    @VisibleForTesting
    @NonNull
    public BasicIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new BasicIdlingResource();
        }
        return mIdlingResource;
    }
}
