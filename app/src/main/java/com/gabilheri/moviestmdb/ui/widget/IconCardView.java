package com.gabilheri.moviestmdb.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gabilheri.moviestmdb.R;

import butterknife.BindView;

/**
 * Created by user on 9/17/2017.
 */

public class IconCardView extends BaseCustomCardView {
    @BindView(R.id.layout_option_card)
    RelativeLayout mLayout;

    @BindView(R.id.image_option)
    ImageView mIcon;

    @BindView(R.id.text_option_title)
    TextView mTitle;

    @BindView(R.id.text_option_value)
    TextView mValue;

    public IconCardView(Context context) {
        super(context);
    }

    @Override
    protected int getCardLayout() {
        return R.layout.view_options_item;
    }

    public void setMainImageDimensions(int width, int height) {
        ViewGroup.LayoutParams lp = mLayout.getLayoutParams();
        lp.width = width;
        lp.height = height;
        mLayout.setLayoutParams(lp);
    }

    public void setOptionTitleText(String titleText) {
        mTitle.setText(titleText);
    }

    public void setOptionValueText(String valueText) {
        mValue.setText(valueText);
    }

    public void setOptionIcon(Drawable drawable) {
        mIcon.setImageDrawable(drawable);

    }
}
