package com.coderschool.assignment1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duongthoai on 10/15/16.
 */

public class Movie extends BaseModel {
    public String getPosterPath() {
        return posterPath;
    }

    public String getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String[] getGenreId() {
        return genreId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginaLanguage() {
        return originaLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public boolean isVideo() {
        return video;
    }

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("adult")
    private String adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("genre_id")
    private String[] genreId;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("original_language")
    private String originaLanguage;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backDropPath;


    @SerializedName("popularity")
    private String popularity;

    @SerializedName("vote_count")
    private String voteCount;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("video")
    private boolean video;

    public String getReleaseDate() {
        return releaseDate;
    }

    @SerializedName("release_date")
    private String releaseDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.posterPath);
        dest.writeString(this.adult);
        dest.writeString(this.overview);
        dest.writeStringArray(this.genreId);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originaLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backDropPath);
        dest.writeString(this.popularity);
        dest.writeString(this.voteCount);
        dest.writeString(this.voteAverage);
        dest.writeString(this.releaseDate);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        super(in);
        this.posterPath = in.readString();
        this.adult = in.readString();
        this.overview = in.readString();
        this.genreId = in.createStringArray();
        this.originalTitle = in.readString();
        this.originaLanguage = in.readString();
        this.title = in.readString();
        this.backDropPath = in.readString();
        this.popularity = in.readString();
        this.voteCount = in.readString();
        this.voteAverage = in.readString();
        this.releaseDate = in.readString();
        this.video = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
