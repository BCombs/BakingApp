/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredients;

    IngredientAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return mIngredients == null ?  0 : mIngredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_tv)
        TextView mIngredientTv;

        @BindView(R.id.quantity_tv)
        TextView mQuantityTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(Ingredient ingredient) {
            String ingredientName = toUpperCase(ingredient.getIngredientName());
            mIngredientTv.setText(ingredientName);
            String amount = ingredient.getQuantity() + " " + ingredient.getMeasurement();
            mQuantityTextView.setText(amount);
        }

        private String toUpperCase(String ingredientName) {
            ingredientName = ingredientName.substring(0,1).toUpperCase()
                    + ingredientName.substring(1);

            return ingredientName;
        }
    }
}
