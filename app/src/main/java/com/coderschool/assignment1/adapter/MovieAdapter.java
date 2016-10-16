package com.coderschool.assignment1.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coderschool.assignment1.DetailActivity;
import com.coderschool.assignment1.R;
import com.coderschool.assignment1.model.Movie;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by duongthoai on 10/15/16.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String IMG_PATH = "https://image.tmdb.org/t/p/w500";
    private final int POPULAR = 1;
    private final int LESS_POPULAR = 2;
    private List<Movie> mMovieList;
    protected Context mContext;

    public MovieAdapter(Context ctx, List<Movie> list) {
        mMovieList = list;
        mContext = ctx;
    }

    public Movie getDateByIndex(int pos) {
        return mMovieList.get(pos);
    }

    @Override
    public int getItemViewType(int position) {
        if (Float.parseFloat(mMovieList.get(position).getVoteAverage()) >= 5) {
            return POPULAR;
        } else {
            return LESS_POPULAR;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == POPULAR) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.popular_movie_item, parent, false);
            return new PopularMovieViewHolder(v);
        } else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
            return new MovieViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Movie item = mMovieList.get(position);

        if (holder instanceof PopularMovieViewHolder) {
            PopularMovieViewHolder viewHolder = (PopularMovieViewHolder) holder;
            ((PopularMovieViewHolder) holder).playImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, DetailActivity.class);
                    i.putExtra(DetailActivity.MOVIE_KEY, item);
                    mContext.startActivity(i);
                }
            });
            Glide.with(mContext).load(IMG_PATH + item.getBackDropPath()).placeholder(R.drawable.placeholder).into(viewHolder.posterImg);
        } else if (holder instanceof MovieViewHolder) {
            MovieViewHolder viewHolder = (MovieViewHolder) holder;

            viewHolder.titleTxt.setText(item.getTitle());
            viewHolder.descTxt.setText(item.getOverview());
            String url = "";
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                url = item.getPosterPath();
            } else {
                url = item.getBackDropPath();
            }
            Glide.with(mContext).load(IMG_PATH + url).bitmapTransform(new RoundedCornersTransformation(mContext, 10, 20)).placeholder(R.drawable.placeholder).into(viewHolder.posterImg);
        }
    }

    @Override
    public int getItemCount() {
        if (mMovieList == null) {
            return 0;
        }
        return mMovieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImg;
        TextView titleTxt;
        TextView descTxt;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImg = (ImageView) itemView.findViewById(R.id.img_poster);
            titleTxt = (TextView) itemView.findViewById(R.id.tv_title);
            descTxt = (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }

    public class PopularMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImg;
        ImageView playImg;

        public PopularMovieViewHolder(View itemView) {
            super(itemView);
            posterImg = (ImageView) itemView.findViewById(R.id.img_poster);
            playImg = (ImageView) itemView.findViewById(R.id.img_play);
        }
    }
}
