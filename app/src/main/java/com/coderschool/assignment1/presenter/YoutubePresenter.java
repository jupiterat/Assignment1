package com.coderschool.assignment1.presenter;

import android.util.Log;

import com.coderschool.assignment1.DetailActivity;
import com.coderschool.assignment1.api.ApiService;
import com.coderschool.assignment1.model.MovieDetail;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by duongthoai on 10/15/16.
 */

public class YoutubePresenter {
    private static final String TAG = YoutubePresenter.class.getSimpleName();
    private DetailActivity mDetailView;
    private ApiService mApiService;
    private Subscription subscription;

    public YoutubePresenter(DetailActivity view, ApiService service) {
        mDetailView = view;
        mApiService = service;
    }

    public void loadYoutubeVideo(String id) {
        Log.e(TAG, "loadYoutubeVideo: id " + id);
        Observable<MovieDetail> movieObservable = (Observable<MovieDetail>) mApiService.getPreparedObservable(mApiService.getApiInterface().getMovieURL(id), MovieDetail.class,
                true, true);
        subscription = movieObservable.subscribe(new Observer<MovieDetail>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError" + e.getMessage());
            }

            @Override
            public void onNext(MovieDetail result) {
                Log.e(TAG, "onNext: " + result);
                mDetailView.loadYoutubeVideo(result.getLinkList().get(0).getSource());
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
