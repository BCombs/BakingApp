/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailListFragment extends Fragment {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detail_list_rv)
    RecyclerView mDetailListRv;
    private DetailListAdapter mAdapter;
    private Recipe mRecipe;
    private Parcelable mLayoutState;
    private FragmentCommunicator mCommunicator;

    public DetailListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.detail_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        if (getArguments() != null && getArguments().containsKey("recipe")) {
            mRecipe = getArguments().getParcelable("recipe");
        }

        // Set title Bar
        if (mRecipe != null) {
            if (getActivity() != null) {
                String title =
                        mRecipe.getName() +
                                " (" + mRecipe.getServings() +
                                " " + getActivity().getResources()
                                .getString(R.string.servings) + ")";
                ((MainActivity) getActivity()).setAppBarTitle(title);
            }
        }

        mCommunicator = (FragmentCommunicator) getActivity();

        initRecyclerView();
    }

    private void initRecyclerView() {

        if (getActivity() != null) {
            mDetailListRv.addItemDecoration(new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL));
        }

        if (getContext() != null) {
            mDetailListRv.setLayoutManager(
                    new LinearLayoutManager(
                            getContext(), LinearLayoutManager.VERTICAL, false));
        }

        mAdapter = new DetailListAdapter(mRecipe.getSteps(), new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mCommunicator.updateUi(position);
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

    interface OnItemClickListener {
        void onClick(int position);
    }
}
