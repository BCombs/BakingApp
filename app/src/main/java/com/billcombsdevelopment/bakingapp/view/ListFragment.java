/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Recipe;
import com.billcombsdevelopment.bakingapp.network.DataCallback;
import com.billcombsdevelopment.bakingapp.network.Requests;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment implements DataCallback {

    @BindView(R.id.list_fragment_rv)
    RecyclerView mListRecyclerView;
    private ListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void loadData() {
        Requests request = new Requests();
        request.getJson(this);
    }

    private void initRecyclerView(List<Recipe> recipeList) {
        mListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new ListAdapter(recipeList, new OnItemClickListener() {
            @Override
            public void onClick(Recipe recipe) {

                Fragment detailFragment = new DetailFragment();
                Bundle args = new Bundle();
                args.putParcelable("recipe", recipe);
                detailFragment.setArguments(args);

                FragmentTransaction transaction =
                        getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_containter, detailFragment, "DetailFragment");
                transaction.addToBackStack("ListFragment");
                transaction.commit();

            }
        });
        mListRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(List<Recipe> recipeList) {
        initRecyclerView(recipeList);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    interface OnItemClickListener {
        void onClick(Recipe recipe);
    }
}