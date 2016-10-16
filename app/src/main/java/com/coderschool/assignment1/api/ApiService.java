package com.coderschool.assignment1.api;

import android.support.v4.util.LruCache;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duongthoai on 10/15/16.
 */

public class ApiService {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private OkHttpClient okHttpClient;
    private LruCache<Class<?>, Observable<?>> apiObservables;
    APIInterface apiInterface;

    public ApiService() {
        okHttpClient = getClient();
        apiObservables = new LruCache<>(10);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        apiInterface = retrofit.create(APIInterface.class);
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("api_key","a07e22bc18f5cb106bfe4cc1f83ad8ed").build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return builder.build();
    }

    public APIInterface getApiInterface() {
        return apiInterface;
    }

    public Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz,
                                               boolean cacheObservable, boolean useCache) {
        Observable<?> preparedObservable = null;
        if (useCache) {
            preparedObservable = apiObservables.get(clazz);
        }
        if (preparedObservable != null) {
            return preparedObservable;
        }
        preparedObservable = unPreparedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        if (cacheObservable) {
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }
        return preparedObservable;
    }


}
