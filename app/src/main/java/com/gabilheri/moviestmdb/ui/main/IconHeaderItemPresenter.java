package com.gabilheri.moviestmdb.ui.main;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabilheri.moviestmdb.R;

/**
 * Created by user on 9/17/2017.
 */
// info - remember to use this presenter RowHeaderPresenter to handle icon in the head view
public class IconHeaderItemPresenter extends RowHeaderPresenter {
    private float mUnselectedAlpha;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        mUnselectedAlpha = viewGroup.getResources()
                .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);

        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.header_icon, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        HeaderItem headerItem = ((ListRow) o).getHeaderItem();
        View rootView = viewHolder.view;

        rootView.setAlpha(mUnselectedAlpha);
        TextView label = (TextView) rootView.findViewById(R.id.header_label);
        label.setText(headerItem.getName());

        // info 1 - add multiple icons beside the text by put inside bind method
        setIconDrawable(headerItem.getName(), rootView);

//        ImageView iconView = (ImageView) rootView.findViewById(R.id.header_icon);
//        Drawable icon = rootView.getResources().getDrawable(R.drawable.ic_home, null);
//        iconView.setImageDrawable(icon);
//
//        TextView label = (TextView) rootView.findViewById(R.id.header_label);
//        label.setText(headerItem.getName());
    }

    // compare with bind method
    public void setIconDrawable(String name, View rootView) {
        Resources res = rootView.getResources();
        String[] categories = res.getStringArray(R.array.categories);
        TypedArray resources = res.obtainTypedArray(R.array.icons);
        int drawableResource = 0;

        if (name.equals(res.getString(R.string.header_text_options))) {
            drawableResource = R.drawable.ic_settings_white_24dp;
        } else {
            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(name)) {
                    drawableResource = resources.getResourceId(i, 0);
                    break;
                }
            }
        }
        resources.recycle();

        if (drawableResource != 0) {
            ImageView iconView = (ImageView) rootView.findViewById(R.id.header_icon);
            Drawable icon = res.getDrawable(drawableResource, null);
            iconView.setImageDrawable(icon);
        }
    }


    // TODO: TEMP - remove me when leanback onCreateViewHolder no longer sets the mUnselectAlpha,AND
    // also assumes the xml inflation will return a RowHeaderView
    @Override
    protected void onSelectLevelChanged(RowHeaderPresenter.ViewHolder holder) {
        // this is a temporary fix
        holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() *
                (1.0f - mUnselectedAlpha));
    }
}
