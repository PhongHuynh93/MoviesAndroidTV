package com.gabilheri.moviestmdb.ui.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;

import com.gabilheri.moviestmdb.ui.widget.MovieCardView;


/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */

public class MoviePresenter extends Presenter {
    private Context mContext;

    public MoviePresenter() {
    }

    public MoviePresenter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(new MovieCardView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ((MovieCardView) viewHolder.view).bind((Movie) item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
