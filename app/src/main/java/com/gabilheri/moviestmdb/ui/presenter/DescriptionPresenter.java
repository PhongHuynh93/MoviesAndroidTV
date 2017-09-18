package com.gabilheri.moviestmdb.ui.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

/**
 * Created by CPU11112-local on 9/18/2017.
 */
//info - not need onCreateViewHolder, so only need to extends AbstractDetailsDescriptionPresenter to has default layout
public class DescriptionPresenter extends AbstractDetailsDescriptionPresenter  {
    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        viewHolder.getTitle().setText(((Movie) item).getTitle());
//        viewHolder.getSubtitle().setText(((Movie) item).getStudio());
    }
}
