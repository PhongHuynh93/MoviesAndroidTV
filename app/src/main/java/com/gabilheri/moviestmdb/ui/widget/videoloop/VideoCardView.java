package com.gabilheri.moviestmdb.ui.widget.videoloop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.widget.BaseCustomCardView;
import com.gabilheri.moviestmdb.util.NetworkUtil;

import butterknife.BindView;

/**
 * Created by user on 10/7/2017.
 */

public class VideoCardView extends BaseCustomCardView {
    public static final int CARD_TYPE_FLAG_IMAGE_ONLY = 0;
    public static final int CARD_TYPE_FLAG_TITLE = 1;
    public static final int CARD_TYPE_FLAG_CONTENT = 2;

    @BindView(R.id.layout_preview_card)
    PreviewCardView mPreviewCard;

    @BindView(R.id.info_field)
    ViewGroup mInfoArea;

    private TextView mTitleView;
    private TextView mContentView;
    private boolean mAttachedToWindow;

    public VideoCardView(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAttachedToWindow = true;
        ImageView mImageView = mPreviewCard.getImageView();
        if (mImageView.getAlpha() == 0) fadeIn();
    }

    @Override
    protected int getCardLayout() {
        return R.layout.lb_video_card_view;
    }

    @Override
    protected void getCardAttrs(LayoutInflater inflater, TypedArray cardAttrs) {
        super.getCardAttrs(inflater, cardAttrs);
        int cardType = cardAttrs.getInt(R.styleable.lbImageCardView_lbImageCardViewType, CARD_TYPE_FLAG_IMAGE_ONLY);
        boolean hasImageOnly = cardType == CARD_TYPE_FLAG_IMAGE_ONLY;
        boolean hasTitle = (cardType & CARD_TYPE_FLAG_TITLE) == CARD_TYPE_FLAG_TITLE;
        boolean hasContent = (cardType & CARD_TYPE_FLAG_CONTENT) == CARD_TYPE_FLAG_CONTENT;

        if (hasImageOnly) {
            removeView(mInfoArea);
            cardAttrs.recycle();
            return;
        }

        // Create children
        if (hasTitle) {
            mTitleView = (TextView) inflater.inflate(R.layout.lb_image_card_view_themed_title, mInfoArea, false);
            mInfoArea.addView(mTitleView);
        }

        if (hasContent) {
            mContentView = (TextView) inflater.inflate(R.layout.lb_image_card_view_themed_content, mInfoArea, false);
            mInfoArea.addView(mContentView);
        }

        // Backward compatibility: Newly created ImageCardViews should change
        // the InfoArea's background color in XML using the corresponding style.
        // However, since older implementations might make use of the
        // 'infoAreaBackground' attribute, we have to make sure to support it.
        // If the user has set a specific value here, it will differ from null.
        // In this case, we do want to override the value set in the style.
        Drawable background = cardAttrs.getDrawable(R.styleable.lbImageCardView_infoAreaBackground);
        if (null != background) {
            setInfoAreaBackground(background);
        }
    }

    /**
     * Sets the info area background drawable.
     */
    public void setInfoAreaBackground(Drawable drawable) {
        if (mInfoArea != null) {
            mInfoArea.setBackground(drawable);
        }
    }

    private void fadeIn() {
        ImageView mImageView = mPreviewCard.getImageView();
        mImageView.setAlpha(0f);
        if (mAttachedToWindow) {
            int duration =
                    mImageView.getResources().getInteger(android.R.integer.config_shortAnimTime);
            mImageView.animate().alpha(1f).setDuration(duration);
        }
    }


    /**
     * Returns the main image view.
     */
    public final ImageView getMainImageView() {
        return mPreviewCard.getImageView();
    }

    /**
     * Sets the layout dimensions of the ImageView.
     */
    public void setMainContainerDimensions(int width, int height) {
        ViewGroup.LayoutParams lp = mPreviewCard.getLayoutParams();
        lp.width = width;
        lp.height = height;
        mPreviewCard.setLayoutParams(lp);
    }

    public void setVideoUrl(String url) {
        mPreviewCard.setVideoUrl(url);
    }

    public void startVideo() {
        if (NetworkUtil.isNetworkConnected(getContext())) {
            mPreviewCard.setLoading();
        }
    }

    public void stopVideo() {
        mPreviewCard.setFinished();
    }

    /**
     * Sets the title text.
     */
    public void setTitleText(CharSequence text) {
        if (mTitleView == null) {
            return;
        }
        mTitleView.setText(text);
    }

    /**
     * Sets the content text.
     */
    public void setContentText(CharSequence text) {
        if (mContentView == null) {
            return;
        }
        mContentView.setText(text);
    }

}
