package com.gabilheri.moviestmdb.ui.widget.videoloop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gabilheri.moviestmdb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 10/7/2017.
 */

public class PreviewCardView extends FrameLayout {
    @BindView(R.id.main_container)
    FrameLayout mMainContainer;

    @BindView(R.id.main_video)
    LoopingVideoView mVideoView;

    @BindView(R.id.main_image)
    ImageView mImageView;

    @BindView(R.id.view_overlay)
    View mOverlayView;

    @BindView(R.id.progress_card)
    ProgressBar mProgressCard;

    private String mVideoUrl;

    public PreviewCardView(Context context) {
        super(context);
        init();
    }

    public PreviewCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PreviewCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PreviewCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.widget_preview_card, this);
        ButterKnife.bind(view);
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    // step - show videoview when loading
    public void setLoading() {
        mOverlayView.setVisibility(View.VISIBLE);
        mProgressCard.setVisibility(View.VISIBLE);
        mVideoView.setVisibility(View.VISIBLE);
        mVideoView.setupMediaPlayer(mVideoUrl, () -> {
            mOverlayView.setVisibility(View.INVISIBLE);
            mProgressCard.setVisibility(View.INVISIBLE);
            mImageView.setVisibility(View.INVISIBLE);
        });
    }

    // step - show image when done videoview
    public void setFinished() {
        mVideoView.setVisibility(View.INVISIBLE);
        mVideoView.stopMediaPlayer();
        mImageView.setVisibility(View.VISIBLE);
        mOverlayView.setVisibility(View.INVISIBLE);
        mProgressCard.setVisibility(View.INVISIBLE);
    }
}
