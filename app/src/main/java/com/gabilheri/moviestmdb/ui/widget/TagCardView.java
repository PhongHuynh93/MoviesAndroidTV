package com.gabilheri.moviestmdb.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabilheri.moviestmdb.R;

import butterknife.BindView;

/**
 * Created by CPU11112-local on 9/20/2017.
 */

public class TagCardView extends BaseCustomCardView {
    @BindView(R.id.text_tag_name)
    TextView mTagNameText;

    @BindView(R.id.image_icon)
    ImageView mResultImage;

    public TagCardView(Context context) {
        super(context, null);
    }

    @Override
    protected int getCardLayout() {
        return R.layout.view_tag_card;
    }

    public void setCardText(String string) {
        mTagNameText.setText(string);
    }

    public void setCardIcon(int resource) {
        mResultImage.setImageDrawable(ContextCompat.getDrawable(getContext(), resource));
    }
}
