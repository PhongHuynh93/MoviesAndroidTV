package com.gabilheri.moviestmdb.ui.widget.videoloop;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by user on 10/7/2017.
 */

public class LoopingVideoView extends VideoView {
    private MediaPlayer mMediaPlayer;

    public LoopingVideoView(Context context) {
        super(context);
    }

    public LoopingVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoopingVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoopingVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setupMediaPlayer(String url, final OnVideoReadyListener onVideoReadyListener) {
        setOnPreparedListener(mp -> {
            mMediaPlayer = mp;
            mMediaPlayer.setOnBufferingUpdateListener((mp1, percent) -> {
                if (percent > 20) onVideoReadyListener.onVideoReady();
            });
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setVolume(0, 0);
            mMediaPlayer.start();
        });
        setVideoURI(Uri.parse(url));
    }

    public void stopMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    public interface OnVideoReadyListener {
        void onVideoReady();
    }
}
