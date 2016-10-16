package com.coderschool.assignment1.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duongthoai on 10/15/16.
 */

public class MovieDetail extends BaseModel {
    public String[] getQuickTime() {
        return quickTime;
    }

    public List<Youtube> getLinkList() {
        return linkList;
    }

    @SerializedName("quicktime")
    private String[] quickTime;
    @SerializedName("youtube")
    private List<Youtube> linkList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringArray(this.quickTime);
        dest.writeList(this.linkList);
    }

    public MovieDetail() {
    }

    protected MovieDetail(Parcel in) {
        super(in);
        this.quickTime = in.createStringArray();
        this.linkList = new ArrayList<Youtube>();
        in.readList(this.linkList, Youtube.class.getClassLoader());
    }

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel source) {
            return new MovieDetail(source);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };
}
