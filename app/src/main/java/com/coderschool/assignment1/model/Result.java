package com.coderschool.assignment1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by duongthoai on 10/15/16.
 */

public class Result {
    @SerializedName("page")
    private String page;
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    @SerializedName("total_pages")

    private String totalPage;
    @SerializedName("total_results")
    private String totalResult;
}
