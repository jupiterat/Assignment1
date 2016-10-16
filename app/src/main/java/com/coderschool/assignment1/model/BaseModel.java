package com.coderschool.assignment1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duongthoai on 10/15/16.
 */

public class BaseModel implements Parcelable {
    public String getId() {
        return id;
    }

    @SerializedName("id")
    private String id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
    }

    public BaseModel() {
    }

    protected BaseModel(Parcel in) {
        this.id = in.readString();
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel source) {
            return new BaseModel(source);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
