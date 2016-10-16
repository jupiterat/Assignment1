package com.coderschool.assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.coderschool.assignment1.adapter.MovieAdapter;
import com.coderschool.assignment1.api.ApiService;
import com.coderschool.assignment1.listener.RecyclerItemClickListener;
import com.coderschool.assignment1.model.Movie;
import com.coderschool.assignment1.presenter.MoviePresenter;

import java.util.List;

/**
 * Created by duongthoai on 10/15/16.
 */

public class HomeFragment extends Fragment {
    private final String TAG = HomeFragment.class.getSimpleName();
    private SwipeRefreshLayout mListRefeshLayout;
    private RecyclerView mListRecycler;
    private ApiService mApiService;
    private MoviePresenter mMoviePresenter;
    private MovieAdapter mMovieAdapter;
    private MainActivity activity;


    public static HomeFragment makeInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = ((CSApplication) activity.getApplication()).getmApiService();
        mMoviePresenter = new MoviePresenter(this, mApiService);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null);
        mListRefeshLayout = (SwipeRefreshLayout) v.findViewById(R.id.sw_container);
        mListRefeshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh");
                loadData();

            }
        });
        mListRecycler = (RecyclerView) v.findViewById(R.id.rv_list);
        mListRecycler.addOnItemTouchListener(new RecyclerItemClickListener(activity, mListRecycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e(TAG, "onItemClick");
                startDetailActivity(mMovieAdapter.getDateByIndex(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        mListRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mMovieAdapter != null) {
            mListRecycler.setAdapter(mMovieAdapter);
        } else {
            loadData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMoviePresenter.unSubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Glide.get(activity).clearMemory();
    }

    private void loadData() {
        Log.e(TAG, "displayMovies");
        mMoviePresenter.loadMovies();
    }

    public void displayMovies(List<Movie> movies) {
        Log.e(TAG, "displayMovies");
        mMovieAdapter = new MovieAdapter(activity, movies);
        mListRecycler.setAdapter(mMovieAdapter);
        //
        if (mListRefeshLayout.isRefreshing()) {
            Log.e(TAG, "isRefreshing");
            mListRefeshLayout.setRefreshing(false);
        }

    }

    private void startDetailActivity(Movie movie) {
        Intent i = new Intent(activity, DetailActivity.class);
        i.putExtra(DetailActivity.MOVIE_KEY, movie);
        startActivity(i);
    }
}
