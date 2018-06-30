/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Recipe;
import com.billcombsdevelopment.bakingapp.ui.OnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ListViewHolder> {

    private final List<Recipe> mRecipes;
    private final OnClickListener mListener;

    public RecipeListAdapter(List<Recipe> recipes, OnClickListener listener) {
        mRecipes = recipes;
        mListener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.bind(recipe, position, mListener);
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

        ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Recipe recipe, final int position,
                  final OnClickListener listener) {

            if (!recipe.getImageUrl().isEmpty()) {
                // An image is available
                Picasso.with(itemView.getContext())
                        .load(recipe.getImageUrl())
                        .placeholder(R.drawable.ic_cupcake)
                        .error(R.drawable.ic_cupcake)
                        .noFade()
                        .into(mFoodImageView);
            } else {
                // No image available, set to default
                mFoodImageView.setImageResource(R.drawable.ic_cupcake);
            }

            mFoodTextView.setText(recipe.getName());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onClick(position);
                }
            });
        }
    }
}
