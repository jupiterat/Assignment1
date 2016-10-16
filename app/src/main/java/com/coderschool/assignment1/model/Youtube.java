package com.coderschool.assignment1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duongthoai on 10/15/16.
 */

public class Youtube implements Parcelable {
    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    @SerializedName("name")
    private String name;
    @SerializedName("size")
    private String size;
    @SerializedName("source")
    private String source;
    @SerializedName("type")
    private String type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.size);
        dest.writeString(this.source);
        dest.writeString(this.type);
    }

    public Youtube() {
    }

    protected Youtube(Parcel in) {
        this.name = in.readString();
        this.size = in.readString();
        this.source = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Youtube> CREATOR = new Parcelable.Creator<Youtube>() {
        @Override
        public Youtube createFromParcel(Parcel source) {
            return new Youtube(source);
        }

        @Override
        public Youtube[] newArray(int size) {
            return new Youtube[size];
        }
    };
}
