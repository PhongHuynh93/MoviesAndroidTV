package com.gabilheri.moviestmdb.ui.main;

import android.graphics.Color;
import android.support.v17.leanback.widget.Presenter;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabilheri.moviestmdb.R;

import static com.gabilheri.moviestmdb.util.Constant.GRID_ITEM_HEIGHT;

/**
 * Created by user on 9/17/2017.
 */

public class GridItemPresenter extends Presenter {

    private static final int GRID_ITEM_WIDTH = 200;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        TextView view = new TextView(parent.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setBackgroundColor(parent.getContext().getResources().getColor(R.color.accent_color));
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ((TextView) viewHolder.view).setText((String) item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
    }
}
