package com.coderschool.assignment1.presenter;

import android.util.Log;

import com.coderschool.assignment1.HomeFragment;
import com.coderschool.assignment1.api.ApiService;
import com.coderschool.assignment1.model.Movie;
import com.coderschool.assignment1.model.Result;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by duongthoai on 10/15/16.
 */

public class MoviePresenter {
    private static final String TAG = MoviePresenter.class.getSimpleName();
    private HomeFragment mHomeView;
    private ApiService mApiService;
    private Subscription subscription;

    public MoviePresenter(HomeFragment view, ApiService service) {
        mHomeView = view;
        mApiService = service;
    }

    public void loadMovies() {
        Log.e(TAG, "loadMovies");
        Observable<Result> movieObservable = (Observable<Result>) mApiService.getPreparedObservable(mApiService.getApiInterface().getMovies(), Movie.class,
                true, true);
        subscription = movieObservable.subscribe(new Observer<Result>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onNext(Result result) {
                Log.e(TAG, "onNext");
                if (result != null && result.getMovies() != null) {
                    mHomeView.displayMovies(result.getMovies());
                } else {

                }
            }
        });
    }

    public void unSubscribe() {
        Log.e(TAG, "rxUnSubscribe");
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
