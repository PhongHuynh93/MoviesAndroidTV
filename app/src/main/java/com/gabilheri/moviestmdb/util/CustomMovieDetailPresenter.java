package com.gabilheri.moviestmdb.util;

import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;

/**
 * Created by CPU11112-local on 9/15/2017.
 */

public class CustomMovieDetailPresenter extends FullWidthDetailsOverviewRowPresenter {

    public CustomMovieDetailPresenter(Presenter detailsPresenter, DetailsOverviewLogoPresenter logoPresenter) {
        super(detailsPresenter, logoPresenter);
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        // The only difference here is that instead of creating a new ViewHolder we are just accessing the already created one.
        FullWidthDetailsOverviewRowPresenter.ViewHolder vh = (FullWidthDetailsOverviewRowPresenter.ViewHolder) holder;
        View v = vh.getOverviewView();
        v.setBackgroundColor(getBackgroundColor());
        v.findViewById(android.support.v17.leanback.R.id.details_overview_actions_background)
                .setBackgroundColor(getActionsBackgroundColor());
    }
}
