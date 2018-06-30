/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Ingredient;
import com.billcombsdevelopment.bakingapp.ui.adapters.IngredientAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.ingredients_rv)
    RecyclerView mIngredientsRv;
    private IngredientAdapter mAdapter;
    private List<Ingredient> mIngredients;

    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.ingredients_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        ButterKnife.bind(this, view);

        if (getArguments() != null && getArguments().containsKey("ingredients")) {
            mIngredients = getArguments().getParcelableArrayList("ingredients");
        }

        initRecyclerView();
    }

    private void initRecyclerView() {

        if (getActivity() != null) {
            mIngredientsRv.addItemDecoration(new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL));
        }
        mIngredientsRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mAdapter = new IngredientAdapter(mIngredients);
        mIngredientsRv.setAdapter(mAdapter);

    }
}
