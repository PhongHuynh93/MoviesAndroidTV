package com.gabilheri.moviestmdb.util;

import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CPU11112-local on 9/15/2017.
 */

public class CustomMovieDetailPresenter extends FullWidthDetailsOverviewRowPresenter {
    private int mPreviousState = STATE_FULL;

    public CustomMovieDetailPresenter(Presenter detailsPresenter, DetailsOverviewLogoPresenter logoPresenter) {
        super(detailsPresenter, logoPresenter);
        // Setting the initial state to FULL prevents the OverviewRow from starting in a weird position
        // It ensures consistency and avoids a bug that makes the poster start off the screen.
        setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_FULL);
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

//    info - make the logo go smooth by using the transition
    @Override
    protected void onLayoutLogo(ViewHolder viewHolder, int oldState, boolean logoChanged) {
        View v = viewHolder.getLogoViewHolder().view;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

        lp.setMarginStart(v.getResources().getDimensionPixelSize(
                android.support.v17.leanback.R.dimen.lb_details_v2_logo_margin_start));
        lp.topMargin = v.getResources().getDimensionPixelSize(android.support.v17.leanback.R.dimen.lb_details_v2_blank_height) - lp.height / 2;

        float offset = v.getResources().getDimensionPixelSize(android.support.v17.leanback.R.dimen.lb_details_v2_actions_height) + v
                .getResources().getDimensionPixelSize(android.support.v17.leanback.R.dimen.lb_details_v2_description_margin_top) + (lp.height / 2);

        switch (viewHolder.getState()) {
            case STATE_FULL:
            default:
                if (mPreviousState == STATE_HALF) {
                    v.animate().translationYBy(-offset);
                }

                break;
            case STATE_HALF:
                if (mPreviousState == STATE_FULL) {
                    v.animate().translationYBy(offset);
                }

                break;
        }
        mPreviousState = viewHolder.getState();
        v.setLayoutParams(lp);
    }
}
