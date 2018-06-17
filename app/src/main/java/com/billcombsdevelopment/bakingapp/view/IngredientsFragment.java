/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {

    @BindView(R.id.ingredients_rv)
    RecyclerView mIngredientsRv;
    private IngredientAdapter mAdapter;
    private List<Ingredient> mIngredients;
    private String mRecipeName;

    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ButterKnife.bind(this, view);

        if (getArguments().containsKey("ingredients")) {
            mIngredients = getArguments().getParcelableArrayList("ingredients");
            Log.d("onViewCreated", "mIngredients size: " + mIngredients.size());
        }

        if(getArguments().containsKey("name")) {
            mRecipeName = getArguments().getString("name");
        }

        initRecyclerView();
    }

    private void initRecyclerView() {

        mIngredientsRv.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mIngredientsRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mIngredientsRv.setNestedScrollingEnabled(false);
        mAdapter = new IngredientAdapter(mIngredients);
        mIngredientsRv.setAdapter(mAdapter);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
