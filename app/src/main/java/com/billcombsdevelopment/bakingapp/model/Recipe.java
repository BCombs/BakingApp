/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("ingredients")
    private ArrayList<Ingredient> mIngredients;
    @SerializedName("steps")
    private ArrayList<Step> mSteps;
    @SerializedName("servings")
    private int mServings;
    @SerializedName("image")
    private String mImageUrl;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps,
                  int servings, String imageUrl) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImageUrl = imageUrl;
    }

    private Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredients = in.readArrayList(Recipe.class.getClassLoader());
        mSteps = in.readArrayList(Recipe.class.getClassLoader());
        mServings = in.readInt();
        mImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeList(mIngredients);
        dest.writeList(mSteps);
        dest.writeInt(mServings);
        dest.writeString(mImageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(ArrayList<Step> steps) {
        mSteps = steps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        mServings = servings;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

}
