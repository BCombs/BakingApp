/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    @SerializedName("quantity")
    private double mQuantity;
    @SerializedName("measure")
    private String mMeasurement;
    @SerializedName("ingredient")
    private String mIngredientName;

    @SuppressWarnings("unused")
    public Ingredient(int quantity, String measurement, String ingredientName) {
        mQuantity = quantity;
        mMeasurement = measurement;
        mIngredientName = ingredientName;
    }

    private Ingredient(Parcel in) {
        mQuantity = in.readDouble();
        mMeasurement = in.readString();
        mIngredientName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mQuantity);
        dest.writeString(mMeasurement);
        dest.writeString(mIngredientName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getQuantity() {
        return mQuantity;
    }

    @SuppressWarnings("unused")
    public void setQuantity(int quantity) {
        this.mQuantity = quantity;
    }

    public String getMeasurement() {
        return mMeasurement;
    }

    @SuppressWarnings("unused")
    public void setMeasurement(String measurement) {
        this.mMeasurement = measurement;
    }

    public String getIngredientName() {
        return mIngredientName;
    }

    @SuppressWarnings("unused")
    public void setIngredientName(String ingredientName) {
        this.mIngredientName = ingredientName;
    }
}
