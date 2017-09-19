package com.gabilheri.moviestmdb.ui.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.example.myapplication.data.models.Option;
import com.gabilheri.moviestmdb.ui.widget.IconCardView;

/**
 * Created by user on 9/17/2017.
 */
// info - card with icon(static)and text like: auto loop
public class IconItemPresenter extends Presenter {
    private static int GRID_ITEM_WIDTH = 350;
    private static int GRID_ITEM_HEIGHT = 400;

    public IconItemPresenter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        IconCardView iconCardView = new IconCardView(parent.getContext());
        return new ViewHolder(iconCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (item instanceof Option) {
            Option option = (Option) item;
            IconCardView optionView = (IconCardView) viewHolder.view;
            optionView.setMainImageDimensions(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT);
            optionView.setOptionTitleText(option.title);
            String value = option.value;
            if (value != null) optionView.setOptionValueText(option.value);
            Context context = viewHolder.view.getContext();
            optionView.setOptionIcon(ContextCompat.getDrawable(context, option.iconResource));
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

}
