/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailViewHolder> {

    private final List<Step> mSteps;
    private final DetailListFragment.OnItemClickListener mListener;

    DetailListAdapter(List<Step> steps, DetailListFragment.OnItemClickListener listener) {
        mSteps = steps;
        mListener = listener;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_list_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        if (position == 0) {
            holder.bind(null, position, mListener);
        } else {
            Step step = mSteps.get(position - 1);
            holder.bind(step, position, mListener);
        }
    }

    @Override
    public int getItemCount() {
        return mSteps == null ? 0 : mSteps.size() + 1;
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detail_list_constraint_layout)
        ConstraintLayout mConstLayout;

        @BindView(R.id.detail_list_linear_layout)
        LinearLayout mLinearLayout;

        @BindView(R.id.detail_list_item_iv)
        ImageView mDetailImageIv;

        @BindView(R.id.detail_list_item_tv)
        TextView mDetailTv;

        @BindView(R.id.step_number_tv)
        TextView mStepNumberTv;

        DetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Step step, final int position, final DetailListFragment.OnItemClickListener listener) {

            // Change background color of every other item
            colorBackground(position);

            switch (position) {
                case 0:
                    // This is the ingredients item
                    mStepNumberTv.setVisibility(View.GONE);
                    mDetailImageIv.setImageResource(R.drawable.ic_ingredients);
                    mDetailImageIv.setVisibility(View.VISIBLE);
                    mDetailTv.setText(R.string.ingredients);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClick(position);
                        }
                    });
                    break;

                case 1:
                    // This is the Recipe Introduction, show Recipe Book Icon
                    mStepNumberTv.setVisibility(View.GONE);
                    mDetailImageIv.setImageResource(R.drawable.ic_steps);
                    mDetailImageIv.setVisibility(View.VISIBLE);
                    mDetailTv.setText(step.getShortDescription());
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClick(position);
                        }
                    });
                    break;

                default:
                    // Steps
                    mDetailImageIv.setVisibility(View.GONE);
                    mStepNumberTv.setVisibility(View.VISIBLE);
                    mStepNumberTv.setText(String.valueOf(step.getStepNumber()));
                    mDetailTv.setText(step.getShortDescription());
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClick(position);
                        }
                    });
            }
        }

        /**
         * Changes the background color of the item
         */
        private void colorBackground(int position) {

            if (position % 2 == 1) {
                mConstLayout.setBackgroundColor(itemView.getResources()
                        .getColor(R.color.colorCardBackground));
                mLinearLayout.setBackgroundColor(itemView.getResources()
                        .getColor(R.color.colorCardBackground));
                mDetailTv.setBackgroundColor(itemView.getResources()
                        .getColor(R.color.colorCardBackground));
                mStepNumberTv.setBackgroundColor(itemView.getResources()
                        .getColor(R.color.colorCardBackground));
            } else {
                mConstLayout.setBackgroundColor(Color.WHITE);
                mLinearLayout.setBackgroundColor(Color.WHITE);
                mDetailTv.setBackgroundColor(Color.WHITE);
                mStepNumberTv.setBackgroundColor(Color.WHITE);
            }

        }
    }
}
