package com.gabilheri.moviestmdb.ui.playbackWithMediaSession;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
public class PlaybackOverlayActivity extends FragmentActivity {
    // step - PlaybackOverlayActivity need to have VideoView field variable “mVideoView” to control video.
    private VideoView mVideoView;
    private PlaybackController mPlaybackController;

    public static Intent buildIntent(Context context, Movie movie) {
        Intent detailsIntent = new Intent(context, PlaybackOverlayActivity.class);
        detailsIntent.putExtra(Movie.class.getSimpleName(), movie);
        return detailsIntent;
    }

    public PlaybackController getPlaybackController() {
        return mPlaybackController;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback_overlay);

        // make this in viewmodel so fragment can access this
        mPlaybackController = new PlaybackController(this);
        loadViews();
    }

    private void loadViews() {
        Movie selectMovie = getIntent().getParcelableExtra(Movie.class.getSimpleName());
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mVideoView.setFocusable(false);
        mVideoView.setFocusableInTouchMode(false);

        mPlaybackController.setVideoView(mVideoView);
        mPlaybackController.setMovie(selectMovie); // it must after video view setting
    }
}

