package com.coderschool.assignment1;

import android.app.Application;

import com.coderschool.assignment1.api.ApiService;

/**
 * Created by duongthoai on 10/15/16.
 */

public class CSApplication extends Application {
    private ApiService mApiService;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiService = new ApiService();
    }

    public ApiService getmApiService() {
        return mApiService;
    }
}
