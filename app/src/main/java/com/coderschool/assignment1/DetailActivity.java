package com.coderschool.assignment1;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.coderschool.assignment1.api.ApiService;
import com.coderschool.assignment1.model.Movie;
import com.coderschool.assignment1.presenter.YoutubePresenter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT;

/**
 * Created by duongthoai on 10/16/16.
 */

public class DetailActivity extends YouTubeBaseActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String MOVIE_KEY = "movie_key";
    private TextView mTitleTxt;
    private TextView mReleaseDateTxt;
    private TextView mDescTxt;
    private RatingBar mRatingBar;
    private Movie mMovie;
    private YouTubePlayerView mYouTubePlayerView;

    private ApiService mApiService;
    private YoutubePresenter mMoviePresenter;
    private YouTubePlayer mYouTubePlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        if (getIntent() != null) {
            mMovie = getIntent().getParcelableExtra(MOVIE_KEY);
        }
        mApiService = ((CSApplication) getApplication()).getmApiService();
        mMoviePresenter = new YoutubePresenter(this, mApiService);
        if (mMovie != null) {
            mMoviePresenter.loadYoutubeVideo(mMovie.getId());
        }
        setContentView(R.layout.movie_detail);
        mTitleTxt = (TextView) findViewById(R.id.tv_title);
        mReleaseDateTxt = (TextView) findViewById(R.id.tv_release_date);
        mDescTxt = (TextView) findViewById(R.id.tv_desc);
        mRatingBar = (RatingBar) findViewById(R.id.rb_vote);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.video_view);

        mYouTubePlayerView.initialize("AIzaSyA7yRojypC6WCIyQbw4Bl3zwShPdGfcHA4",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        mYouTubePlayer = youTubePlayer;


                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
        Drawable progress = mRatingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.WHITE);
        loadMovieDetail();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMoviePresenter.unSubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mYouTubePlayer != null) {
            mYouTubePlayer.release();
            mYouTubePlayer = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mYouTubePlayer.setFullscreen(false);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mYouTubePlayer.setFullscreen(true);
        }
    }

    private void loadMovieDetail() {
        Log.e(TAG, "loadMovieDetail");
        if (mMovie == null) {
            return;
        }
        mTitleTxt.setText(mMovie.getTitle());
        mReleaseDateTxt.setText(mMovie.getReleaseDate());
        mDescTxt.setText(mMovie.getOverview());
        mRatingBar.setRating(Float.parseFloat(mMovie.getVoteAverage()));
    }

    public void loadYoutubeVideo(String source) {
        if (null == mYouTubePlayer) {
            if (Float.parseFloat(mMovie.getVoteAverage()) >= 5) {
                mYouTubePlayer.addFullscreenControlFlag(FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
                mYouTubePlayer.setFullscreen(true);
                mYouTubePlayer.loadVideo(source);
            } else {
                mYouTubePlayer.addFullscreenControlFlag(FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                mYouTubePlayer.setFullscreen(false);
                mYouTubePlayer.cueVideo(source);
            }
        }

    }
}
