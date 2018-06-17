/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {
    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
    @SerializedName("id")
    private int mStepNumber;
    @SerializedName("shortDescription")
    private String mShortDescription;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("videoURL")
    private String mVideoUrl;
    @SerializedName("thumbnailURL")
    private String mThumbnailUrl;

    public Step(int stepNumber, String shortDescription, String description, String videoUrl,
                String thumbnailUrl) {
        mStepNumber = stepNumber;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    protected Step(Parcel in) {
        mStepNumber = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mStepNumber);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
        dest.writeString(mThumbnailUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getmStepNumber() {
        return mStepNumber;
    }

    public void setmStepNumber(int stepNumber) {
        this.mStepNumber = stepNumber;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String shortDescription) {
        this.mShortDescription = shortDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String description) {
        this.mDescription = description;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setmVideoUrl(String videoUrl) {
        this.mVideoUrl = videoUrl;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setmThumbnailUrl(String thumbnailUrl) {
        this.mThumbnailUrl = thumbnailUrl;
    }
}
