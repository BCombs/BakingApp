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

        // If the back stack isn't empty, enable the Up button
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        int backStackHeight = getSupportFragmentManager().getBackStackEntryCount();
                        if (backStackHeight > 0) {
                            getSupportActionBar().setHomeButtonEnabled(true);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        } else {
                            getSupportActionBar().setHomeButtonEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        }
                    }
                });

        if (savedInstanceState == null) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            ListFragment listFragment = new ListFragment();
            transaction.add(R.id.fragment_containter, listFragment, "ListFragment");
            transaction.commit();
        }
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
