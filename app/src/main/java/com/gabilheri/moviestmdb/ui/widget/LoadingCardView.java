package com.gabilheri.moviestmdb.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.gabilheri.moviestmdb.R;

import butterknife.BindView;

/**
 * Created by user on 9/17/2017.
 */

public class LoadingCardView extends BaseCustomCardView {
    @BindView(R.id.progress_indicator)
    ProgressBar mProgressBar;

    public LoadingCardView(Context context) {
        super(context, null);
    }

    @Override
    protected int getCardLayout() {
        return R.layout.view_loading_card;
    }

    public void isLoading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
