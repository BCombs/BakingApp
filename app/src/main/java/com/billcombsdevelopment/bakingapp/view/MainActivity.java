/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.billcombsdevelopment.bakingapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize AppBar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

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

        if (savedInstanceState == null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            RecipeListFragment recipeListFragment = new RecipeListFragment();
            transaction.add(R.id.fragment_containter, recipeListFragment, "RecipeListFragment");
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set the title to App Name
        getSupportActionBar().setTitle(R.string.app_name);
    }

    /**
     * Checks if the back stack is empty or not. If it isn't, enable the Home button, if
     * it is, disable it.
     */
    private void setHomeButton() {
        // If the back stack isn't empty, enable the Up button
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            return;
        }

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return false;
    }
}
