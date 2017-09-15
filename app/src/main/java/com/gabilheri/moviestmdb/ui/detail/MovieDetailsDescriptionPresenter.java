package com.gabilheri.moviestmdb.ui.detail;

import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.data.models.MovieDetails;

/**
 * Created by CPU11112-local on 9/15/2017.
 */

public class MovieDetailsDescriptionPresenter extends Presenter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_details, parent, false);
        return new MovieDetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        MovieDetails movieDetails = (MovieDetails) item;
        MovieDetailsViewHolder holder = (MovieDetailsViewHolder) viewHolder;
        holder.bind(movieDetails);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
