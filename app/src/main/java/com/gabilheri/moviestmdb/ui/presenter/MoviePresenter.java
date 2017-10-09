package com.gabilheri.moviestmdb.ui.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Movie;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.main.MainActivity;
import com.gabilheri.moviestmdb.ui.widget.videoloop.VideoCardView;


/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */

public class MoviePresenter extends Presenter {
    public static final int CARD_WIDTH = 300;
    public static final int CARD_HEIGHT = 300;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Context mContext;
    private Drawable mDefaultCardImage;

    public MoviePresenter() {
    }

    public MoviePresenter(Context context) {
        mContext = context;
    }

    private static void updateCardBackgroundColor(VideoCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        // info - only contains static picture
//        return new ViewHolder(new MovieCardView(parent.getContext()));
        final Context context = parent.getContext();
        sDefaultBackgroundColor = ContextCompat.getColor(context, R.color.primary);
        sSelectedBackgroundColor = ContextCompat.getColor(context, R.color.primary_dark);
        mDefaultCardImage = ContextCompat.getDrawable(context, R.drawable.ic_card_default);

        final VideoCardView cardView = new VideoCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.stopVideo();
            }
        });

        cardView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // step - when focus - start playing the video
                    cardView.startVideo();
                } else {
                    // step - when not focus -stop the video
                    if (mContext instanceof MainActivity) {
                        if (((MainActivity) mContext).isFragmentActive()) {
                            cardView.stopVideo();
                        }
                    }
                    // step - add more fragment that make the video auto loop
//                    else if (mContext instanceof SearchActivity) {
//                        if (((SearchActivity) mContext).isFragmentActive()) {
//                            cardView.stopVideo();
//                        }
//                    } else if (mContext instanceof MainActivity) {
//                        if (((MainActivity) mContext).isFragmentActive()) {
//                            cardView.stopVideo();
//                        }
//                    }
                    else {
                        cardView.stopVideo();
                    }
                }
            }
        });

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        // step - old method to bind the videoview
//        ((MovieCardView) viewHolder.view).bind((Movie) item);
        // step - new method with auto loop video
        if (item instanceof Movie) {
            Movie post = (Movie) item;

//            final VideoCardView cardView = (VideoCardView) viewHolder.view;
            ((VideoCardView) viewHolder.view).bind((Movie) item);
//            if (post.getPosterPath() != null) {
//                cardView.setTitleText(post.getTitle());
//                cardView.setContentText(post.getOverview());
//                cardView.setMainContainerDimensions(CARD_WIDTH, CARD_HEIGHT);
//                cardView.setVideoUrl(Constant.testVideoUrl);
//
//                Glide.with(cardView.getContext())
//                        .load(HttpClientModule.POSTER_URL + post.getPosterPath())
//                        .centerCrop()
//                        .error(mDefaultCardImage)
//                        .into(cardView.getMainImageView());
//            }
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        if (viewHolder.view instanceof ImageCardView) {
            ImageCardView cardView = (ImageCardView) viewHolder.view;
            // Remove references to images so that the garbage collector can free up memory
            cardView.setBadgeImage(null);
            cardView.setMainImage(null);
        }
    }
}
