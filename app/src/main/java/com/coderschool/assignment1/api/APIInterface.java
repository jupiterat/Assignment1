package com.coderschool.assignment1.api;

import com.coderschool.assignment1.model.MovieDetail;
import com.coderschool.assignment1.model.Result;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by duongthoai on 10/15/16.
 */

public interface APIInterface {
    @GET("now_playing")
    Observable<Result> getMovies();

    @GET("{id}/trailers")
    Observable<MovieDetail> getMovieURL(@Path("id") String id);
}
