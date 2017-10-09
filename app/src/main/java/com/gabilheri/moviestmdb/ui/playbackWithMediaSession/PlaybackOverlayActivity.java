package com.gabilheri.moviestmdb.ui.playbackWithMediaSession;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.example.myapplication.Movie;
import com.gabilheri.moviestmdb.R;

/**
 * Note that it is important to differentiate “UI update part” and “Video control part”, because Video control part will move to MediaSession in next chapter.
 * In the source code, I implemented “UI update part” in PlaybackOverlayFragment.java,
 * info - while “Video control part” is implemented in PlaybackOverlayActivity.java.
 * <p>
 * For 3, we create MediaSession in PlaybackOverlayActivity, and control it from MediaController in PlaybackOverlayFragment.
 * For 4, MediaSession’s Metadata is updated using MediaMetadata & PlaybackState class to update “Now playing card”.
 */
public class PlaybackOverlayActivity extends Activity {

    private static final String TAG = PlaybackOverlayActivity.class.getSimpleName();
    String mCategoryName;
    // step - PlaybackOverlayActivity need to have VideoView field variable “mVideoView” to control video.
    private VideoView mVideoView;
    private PlaybackController mPlaybackController;

    private Movie mSelectedMovie;

    public static Intent buildIntent(Context context, Movie movie) {
        Intent detailsIntent = new Intent(context, PlaybackOverlayActivity.class);
        detailsIntent.putExtra(Movie.class.getSimpleName(), movie);
        return detailsIntent;
    }
    //private int mCurrentItem;

    public PlaybackController getPlaybackController() {
        return mPlaybackController;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mSelectedMovie = getIntent().getParcelableExtra(Movie.class.getSimpleName());

        /* NOTE: setMediaController (in createMediaSession) must be executed
         * BEFORE inflating Fragment!
         * NOTE2: sMovieList in VideoProvider must be prepared before instantiating PlaybackController...
         */
        mPlaybackController = new PlaybackController(this);
        //mPlaybackController = new PlaybackController(this, currentItemIndex, VideoProvider.getMovieItems(mCategoryName));

        //mCurrentItem = 0;//(int) mSelectedMovie.getId() - 1;
        //mPlaybackController.setCurrentItem(mCurrentItem);

        setContentView(R.layout.activity_playback_overlay);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mPlaybackController.setVideoView(mVideoView);
        mPlaybackController.setMovie(mSelectedMovie); // it must after video view setting
        loadViews();
    }

    private void loadViews() {
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mVideoView.setFocusable(false);
        mVideoView.setFocusableInTouchMode(false);

        mPlaybackController.setVideoPath(mSelectedMovie.getVideoUrl());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlaybackController.finishPlayback();
    }

    @Override
    public void onPause() {
        super.onPause();
        // step - If this call is successful then the activity will remain visible after onPause() is called, and is allowed to continue playing media in the background.
        // step - After this implementation, when you play video contents in your application and press “Home” key to go back LeanbackLauncher, the video is remain playing in background. .
        if (!requestVisibleBehind(true)) {
            // Try to play behind launcher, but if it fails, stop playback.
            mPlaybackController.playPause(false);
        }
    }

}

