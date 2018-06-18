/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Ingredient;
import com.billcombsdevelopment.bakingapp.model.Recipe;
import com.billcombsdevelopment.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailListFragment extends Fragment {

    @BindView(R.id.detail_list_rv)
    RecyclerView mDetailListRv;
    private DetailListAdapter mAdapter;
    private Recipe mRecipe;
    private String mAppBarTitle;
    private Parcelable mLayoutState;

    public DetailListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        if (getArguments().containsKey("recipe")) {
            mRecipe = getArguments().getParcelable("recipe");
        }

        initRecyclerView();
    }

    public void initRecyclerView() {
        mDetailListRv.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        mDetailListRv.setLayoutManager(
                new LinearLayoutManager(
                        getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new DetailListAdapter(mRecipe.getSteps(), new OnItemClickListener() {
            @Override
            public void onClick(Step step, int position) {
                // User clicked on the Ingredients item
                if (position == 0) {
                    Fragment ingredientsFragment = new IngredientsFragment();

                    // Create the arguments Bundle and pass in the ingredients list and name
                    // of the recipe
                    Bundle args = new Bundle();
                    ArrayList<Ingredient> ingredients = new ArrayList<>(mRecipe.getIngredients());
                    args.putParcelableArrayList("ingredients", ingredients);
                    args.putString("name", mRecipe.getName());
                    args.putString("appBarTitle", mAppBarTitle);
                    ingredientsFragment.setArguments(args);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                            .beginTransaction();

                    transaction.replace(R.id.fragment_containter,
                            ingredientsFragment, "IngredientsFragment");
                    transaction.addToBackStack("DetailListFragment");
                    transaction.commit();

                } else {
                    // User clicked on a step
                    Fragment stepFragment = new StepFragment();

                    // Create the arguments Bundle and pass in the step
                    Bundle args = new Bundle();
                    args.putParcelable("step", step);
                    args.putString("appBarTitle", mAppBarTitle);
                    stepFragment.setArguments(args);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                            .beginTransaction();

                    transaction.replace(R.id.fragment_containter,
                            stepFragment, "StepFragment");
                    transaction.addToBackStack("DetailListFragment");
                    transaction.commit();
                }
            }
        });
        mDetailListRv.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("layoutState", mLayoutState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLayoutState = mDetailListRv.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        mAppBarTitle = mRecipe.getName() + " (" + mRecipe.getServings() + " servings)";
        actionBar.setTitle(mAppBarTitle);
    }

    interface OnItemClickListener {
        void onClick(Step step, int position);
    }
}
