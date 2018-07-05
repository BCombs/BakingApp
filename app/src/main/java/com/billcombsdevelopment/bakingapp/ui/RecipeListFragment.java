/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Recipe;
import com.billcombsdevelopment.bakingapp.ui.adapters.RecipeListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.list_fragment_rv)
    RecyclerView mListRecyclerView;
    private RecipeListAdapter mAdapter;
    private ArrayList<Recipe> mRecipeList;
    private FragmentCommunicator mCommunicator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.recipe_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        // Set the title and get an IdlingResource
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setAppBarTitle(getResources().getString(R.string.app_name));
        }

        if (getArguments() != null) {
            mRecipeList = getArguments().getParcelableArrayList("recipeList");
        }

        mCommunicator = (FragmentCommunicator) getActivity();
        initRecyclerView(mRecipeList);

    }

    private void initRecyclerView(ArrayList<Recipe> recipeList) {

        // Initialize cardWidth in pixels
        int cardWidth = 700;

        // Set the LayoutManager
        mListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                calculateSpanCount(cardWidth)));

        // Instantiate the adapter
        mAdapter = new RecipeListAdapter(recipeList, new OnClickListener() {
            @Override
            public void onClick(int position) {
                mCommunicator.onRecipeSelected(position);
            }
        });

        mListRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Calculates the span count needed for the GridLayoutManager
     *
     * @param cardWidth - Width of the card in pixels
     * @return int - how many columns needed
     */
    private int calculateSpanCount(int cardWidth) {
        // Information about display(size, density, ...)
        DisplayMetrics metrics = new DisplayMetrics();

        if (getActivity() != null) {
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }

        // Get the absolute width of the display in pixels
        float screenWidth = metrics.widthPixels;

        return Math.round(screenWidth / cardWidth);
    }

}
