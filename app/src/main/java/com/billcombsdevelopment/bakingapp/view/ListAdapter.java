/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    List<Recipe> mRecipes;
    ListFragment.OnItemClickListener mListener;

    public ListAdapter(List<Recipe> recipes, ListFragment.OnItemClickListener listener) {
        mRecipes = recipes;
        mListener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.bind(recipe, mListener);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.food_preview_iv)
        ImageView mFoodImageView;
        @BindView(R.id.food_name_tv)
        TextView mFoodTextView;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Recipe recipe, final ListFragment.OnItemClickListener listener) {
            mFoodImageView.setImageResource(R.drawable.ic_cupcake);
            mFoodTextView.setText(recipe.getName());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onClick(recipe);
                }
            });
        }
    }
}
