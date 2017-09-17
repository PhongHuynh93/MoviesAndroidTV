package com.gabilheri.moviestmdb.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v17.leanback.widget.BaseCardView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.gabilheri.moviestmdb.R;

/**
 * Created by user on 9/17/2017.
 */

public class LoadingCardView extends BaseCardView {
    private ProgressBar mProgressBar;

    public LoadingCardView(Context context) {
        this(context, null);
    }

    public LoadingCardView(Context context, int styleResId) {
        super(new ContextThemeWrapper(context, styleResId), null, 0);
        buildLoadingCardView(styleResId);
    }

    public LoadingCardView(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v17.leanback.R.attr.imageCardViewStyle);
    }

    public LoadingCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getStyledContext(context, attrs, defStyleAttr), attrs, defStyleAttr);
        buildLoadingCardView(getImageCardViewStyle(context, attrs, defStyleAttr));
    }

    private static Context getStyledContext(Context context, AttributeSet attrs, int defStyleAttr) {
        int style = getImageCardViewStyle(context, attrs, defStyleAttr);
        return new ContextThemeWrapper(context, style);
    }

    private static int getImageCardViewStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        int style = null == attrs ? 0 : attrs.getStyleAttribute();
        if (0 == style) {
            TypedArray styledAttrs =
                    context.obtainStyledAttributes(
                            android.support.v17.leanback.R.styleable.LeanbackTheme);
            style = styledAttrs.getResourceId(
                    android.support.v17.leanback.R.styleable.LeanbackTheme_imageCardViewStyle, 0);
            styledAttrs.recycle();
        }
        return style;
    }

    private void buildLoadingCardView(int styleResId) {
        setFocusable(false);
        setFocusableInTouchMode(false);
        setCardType(CARD_TYPE_MAIN_ONLY);
        setBackgroundResource(R.color.card_background);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_loading_card, this);
        TypedArray cardAttrs =
                getContext().obtainStyledAttributes(
                        styleResId, android.support.v17.leanback.R.styleable.lbImageCardView);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_indicator);
        cardAttrs.recycle();
    }

    public void isLoading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
