package com.gabilheri.moviestmdb.ui.playbackWithMediaSession;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.myapplication.Movie;
import com.gabilheri.moviestmdb.util.Utils;

import java.util.ArrayList;

/**
 * Displaying a Now Playing Card
 * <a href="https://developer.android.com/training/tv/playback/now-playing.html#card"></a>
 * Created by corochann on 24/7/2015.
 * PlaybackController
 * - owns MediaSession
 * - owns VideoView specified from activity
 * and
 * - manages Movielist
 * - handles media button action
 * <p>
 * <p>
 * missing this method
 * info - When the user selects the Now Playing card, the system opens the app that owns the session. If your app provides a PendingIntent to setSessionActivity(), the system launches the activity you specify, as demonstrated below. If not, the default system intent opens. The activity you specify must provide playback controls that allow users to pause or stop playback.
 * Intent intent = new Intent(mContext, MyActivity.class);
 * PendingIntent pi = PendingIntent.getActivity(context, 99,
 * intent, PendingIntent.FLAG_UPDATE_CURRENT);
 * mSession.setSessionActivity(pi);
 */
public class PlaybackController implements LifecycleObserver {

    public static final int MSG_STOP = 0;
    public static final int MSG_PAUSE = 1;
    public static final int MSG_PLAY = 2;
    public static final int MSG_REWIND = 3;
    public static final int MSG_SKIP_TO_PREVIOUS = 4;
    public static final int MSG_SKIP_TO_NEXT = 5;
    public static final int MSG_FAST_FORWARD = 6;
    public static final int MSG_SET_RATING = 7;
    public static final int MSG_SEEK_TO = 8;
    public static final int MSG_PLAY_PAUSE = 9;
    public static final int MSG_PLAY_FROM_MEDIA_ID = 10;
    public static final int MSG_PLAY_FROM_SEARCH = 11;
    public static final int MSG_SKIP_TO_QUEUE_ITEM = 12;
    /* Constants */
    private static final String TAG = PlaybackController.class.getSimpleName();
    private static final String MEDIA_SESSION_TAG = "AndroidTVappTutorialSession";
    private static ArrayList<Movie> mItems; // =  MovieProvider.getMovieItems(); // new ArrayList<Movie>();
    /* Attributes */
    private Activity mActivity;
    private MediaSession mSession;
    private MediaSessionCallback mMediaSessionCallback;
    private VideoView mVideoView;
    /* Global variables */
    private int mCurrentPlaybackState = PlaybackState.STATE_NONE;
    private int mCurrentItem; // index of current item
    private int mPosition = 0;
    private long mStartTimeMillis;
    private long mDuration = -1;

    public PlaybackController(Activity activity) {
        mActivity = activity;
        createMediaSession(mActivity);

        if (activity instanceof LifecycleOwner) {
            ((LifecycleOwner) activity).getLifecycle().addObserver(this);
        }
    }

    public PlaybackController(Activity activity, int currentItemIndex, ArrayList<Movie> items) {
        mActivity = activity;
        this.setPlaylist(currentItemIndex, items);
        createMediaSession(mActivity);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        // step - If this call is successful then the activity will remain visible after onPause() is called, and is allowed to continue playing media in the background.
        // step - After this implementation, when you play video contents in your application and press “Home” key to go back LeanbackLauncher, the video is remain playing in background. .
        if (!mActivity.requestVisibleBehind(true)) {
            // Try to play behind launcher, but if it fails, stop playback.
            playPause(false);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        finishPlayback();
    }

    public int getCurrentPlaybackState() {
        return mCurrentPlaybackState;
    }

    public void setCurrentPlaybackState(int currentPlaybackState) {
        this.mCurrentPlaybackState = currentPlaybackState;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    public void setCurrentItem(int currentItem) {
        Log.v(TAG, "setCurrentItem: " + currentItem);
        this.mCurrentItem = currentItem;
    }

    public void setPlaylist(int currentItemIndex, ArrayList<Movie> items) {
        mCurrentItem = currentItemIndex;
        mItems = items;
        if (mItems == null) {
            Log.e(TAG, "mItems null!!");
        }
    }

    private void createMediaSession(Activity activity) {
        if (mSession == null) {
            // step - Create a MediaSession when your app is preparing to play media. The following code snippet is an example of how to set the appropriate callback and flags:
            mSession = new MediaSession(activity, MEDIA_SESSION_TAG);
            mMediaSessionCallback = new MediaSessionCallback();
            mSession.setCallback(mMediaSessionCallback);
            // Note: The Now Playing card will display only for a media session with the FLAG_HANDLES_TRANSPORT_CONTROLS flag set.
            // enable remote controller key to control video.
            mSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS |
                    MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);

            // step - The Now Playing card only appears for active sessions. You must call setActive(true) when playback begins. Your app must also request audio focus, as described in Managing Audio Focus.
            // step - when we call setActive(false)
            /**
             * The card is removed from the launcher screen when a setActive(false) call deactivates the media session, or when another app initiates media playback.
             * If playback is completely stopped and there is no active media, your app should deactivate the media session immediately.
             * If playback is paused, your app should deactivate the media session after a delay, usually between 5 to 30 minutes.
             */
            mSession.setActive(true);
            activity.setMediaController(new MediaController(activity, mSession.getSessionToken()));
        }
    }

    public MediaSessionCallback getMediaSessionCallback() {
        return mMediaSessionCallback;
    }

    public void setVideoView(VideoView videoView) {
        mVideoView = videoView;

        /* Callbacks for mVideoView */
        setupCallbacks();

    }

    public void setMovie(Movie movie) {
        // Log.v(TAG, "setMovie: " + movie.toString());
        mVideoView.setVideoPath(movie.getVideoUrl());
    }

    public void setVideoPath(String videoUrl) {
        setPosition(0);
        mVideoView.setVideoPath(videoUrl);
        mStartTimeMillis = 0;
        mDuration = Utils.getDuration(videoUrl);
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        if (position > mDuration) {
            Log.d(TAG, "position: " + position + ", mDuration: " + mDuration);
            mPosition = (int) mDuration;
        } else if (position < 0) {
            mPosition = 0;
            mStartTimeMillis = System.currentTimeMillis();
        } else {
            mPosition = position;
        }
        mStartTimeMillis = System.currentTimeMillis();
        Log.d(TAG, "position set to " + mPosition);
    }

    public int getCurrentPosition() {
        return mVideoView.getCurrentPosition();
    }

    // step - Update the playback state in the MediaSession so the card can show the state of the current media.
    // For example, it will be called in playPause method. When user start play video state will change from STATE_PLAYING to STATE_PAUSED, or vice versa. PlaybackState update is set (notified) to MediaSession.
    private void updatePlaybackState() {
        PlaybackState.Builder stateBuilder = new PlaybackState.Builder()
                .setActions(getAvailableActions());
        int state = PlaybackState.STATE_PLAYING;
        if (mCurrentPlaybackState == PlaybackState.STATE_PAUSED || mCurrentPlaybackState == PlaybackState.STATE_NONE) {
            state = PlaybackState.STATE_PAUSED;
        }
        // stateBuilder.setState(state, mPosition, 1.0f);
        stateBuilder.setState(state, getCurrentPosition(), 1.0f);
        mSession.setPlaybackState(stateBuilder.build());
    }

    private long getAvailableActions() {
        long actions = PlaybackState.ACTION_PLAY |
                PlaybackState.ACTION_PAUSE |
                PlaybackState.ACTION_PLAY_PAUSE |
                PlaybackState.ACTION_REWIND |
                PlaybackState.ACTION_FAST_FORWARD |
                PlaybackState.ACTION_SKIP_TO_PREVIOUS |
                PlaybackState.ACTION_SKIP_TO_NEXT |
                PlaybackState.ACTION_PLAY_FROM_MEDIA_ID |
                PlaybackState.ACTION_PLAY_FROM_SEARCH;
        return actions;
    }

    /**
     * should be called Activity's onDestroy
     */
    public void finishPlayback() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView.suspend();
            mVideoView.setVideoURI(null);
        }
        releaseMediaSession();
    }

    public int getBufferPercentage() {
        return mVideoView.getBufferPercentage();
    }

    public int calcBufferedTime(int currentTime) {
        int bufferedTime;
        bufferedTime = currentTime + (int) ((mDuration - currentTime) * getBufferPercentage()) / 100;
        return bufferedTime;
    }

    private void setupCallbacks() {

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mVideoView.stopPlayback();
                mCurrentPlaybackState = PlaybackState.STATE_NONE;
                return false;
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (mCurrentPlaybackState == PlaybackState.STATE_PLAYING) {
                    mVideoView.start();
                }
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCurrentPlaybackState = PlaybackState.STATE_NONE;
            }
        });
    }

    public void updateMetadata() {
        Log.i(TAG, "updateMetadata: getCurrentItem" + getCurrentItem());
        Movie movie = mItems.get(getCurrentItem());
        mDuration = Utils.getDuration(movie.getVideoUrl());
        updateMetadata(movie);
    }

    /**
     * step - This method of the media session object lets you provide information to the Now Playing card about the track such as the title, subtitle, and various icons.
     * The following example assumes your track's data is stored in a custom data class, MediaData.
     *
     * @param movie
     */
    public void updateMetadata(Movie movie) {
        final MediaMetadata.Builder metadataBuilder = new MediaMetadata.Builder();

        String title = movie.getTitle().replace("_", " -");

        metadataBuilder.putString(MediaMetadata.METADATA_KEY_MEDIA_ID, movie.getId());
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_DISPLAY_TITLE, title);
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE, movie.getStudio());
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_DISPLAY_DESCRIPTION, movie.getOverview());
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_DISPLAY_ICON_URI, movie.getCardImageUrl());
        metadataBuilder.putLong(MediaMetadata.METADATA_KEY_DURATION, mDuration);

        // And at minimum the title and artist for legacy support
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_TITLE, title);
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_ARTIST, movie.getStudio());

        Glide.with(mActivity)
                .load(Uri.parse(movie.getCardImageUrl()))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(500, 500) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {

                        metadataBuilder.putBitmap(MediaMetadata.METADATA_KEY_ART, bitmap);
                        // step -must call this method to set the metadata, and update PlaybackControlsRow
                        mSession.setMetadata(metadataBuilder.build());
                    }
                });
    }

    // step - After create, we must release MediaSession when it finishes.
    public void releaseMediaSession() {
        if (mSession != null) {
            mSession.release();
        }
    }

    /**
     * step - Video control functions are implemented in MediaSessionCallback class. As name suggests, each video control action is implemented in corresponding callback function.
     * This callback is called from 2 ways
     * 1. which is “remote controller media key”  --> when we press the button on the remote
     * 2. “UI video control button in PlaybackControlsRow”. -> which we press the button on the video
     *
     * @param doPlay
     */
    public void playPause(boolean doPlay) {

        if (mCurrentPlaybackState == PlaybackState.STATE_NONE) {
            /* Callbacks for mVideoView */
            setupCallbacks();
        }

        //if (doPlay && mCurrentPlaybackState != PlaybackState.STATE_PLAYING) {
        if (doPlay) { // Play
            Log.d(TAG, "playPause: play");
            if (mCurrentPlaybackState == PlaybackState.STATE_PLAYING) {
                /* if current state is already playing, do nothing */
                return;
            } else {
                mCurrentPlaybackState = PlaybackState.STATE_PLAYING;
                mVideoView.start();
                mStartTimeMillis = System.currentTimeMillis();
            }
        } else { // Pause
            Log.d(TAG, "playPause: pause");
            if (mCurrentPlaybackState == PlaybackState.STATE_PAUSED) {
                /* if current state is already paused, do nothing */
                return;
            } else {
                mCurrentPlaybackState = PlaybackState.STATE_PAUSED;
            }
            setPosition(mVideoView.getCurrentPosition());
            mVideoView.pause();

        }

        updatePlaybackState();
    }

    public void fastForward() {
        if (mDuration != -1) {
            // Fast forward 10 seconds.
            setPosition(getCurrentPosition() + (10 * 1000));
            mVideoView.seekTo(mPosition);
        }

    }

    public void rewind() {
        // rewind 10 seconds
        setPosition(getCurrentPosition() - (10 * 1000));
        mVideoView.seekTo(mPosition);
    }


    private class MediaSessionCallback extends MediaSession.Callback {
        @Override
        public void onPlay() {
            playPause(true);
        }

        @Override
        public void onPause() {
            playPause(false);
        }

        @Override
        public void onSkipToNext() {
            if (++mCurrentItem >= mItems.size()) { // Current Item is set to next here
                mCurrentItem = 0;
            }
            Log.d(TAG, "onSkipToNext: " + mCurrentItem);
            Movie movie = mItems.get(mCurrentItem);
            //Movie movie = VideoProvider.getMovieById(mediaId);
            if (movie != null) {
                setVideoPath(movie.getVideoUrl());
                //mCurrentPlaybackState = PlaybackState.STATE_PAUSED;
                //updateMetadata(movie);
                updateMetadata();
                playPause(mCurrentPlaybackState == PlaybackState.STATE_PLAYING);
            } else {
                Log.e(TAG, "onSkipToNext movie is null!");
            }

        }


        @Override
        public void onSkipToPrevious() {
            if (--mCurrentItem < 0) { // Current Item is set to previous here
                mCurrentItem = mItems.size() - 1;
            }

            Movie movie = mItems.get(mCurrentItem);
            //Movie movie = VideoProvider.getMovieById(mediaId);
            if (movie != null) {
                setVideoPath(movie.getVideoUrl());
                //mCurrentPlaybackState = PlaybackState.STATE_PAUSED;
                updateMetadata();
                playPause(mCurrentPlaybackState == PlaybackState.STATE_PLAYING);
            } else {
                Log.e(TAG, "onSkipToPrevious movie is null!");
            }
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            mCurrentItem = Integer.parseInt(mediaId);
            Movie movie = mItems.get(mCurrentItem);
            //Movie movie = VideoProvider.getMovieById(mediaId);
            if (movie != null) {
                setVideoPath(movie.getVideoUrl());
                // mCurrentPlaybackState = PlaybackState.STATE_PAUSED;
                // updateMetadata(movie);
                updateMetadata();
                playPause(mCurrentPlaybackState == PlaybackState.STATE_PLAYING);
            }
        }

        @Override
        public void onSeekTo(long pos) {
            setPosition((int) pos);
            mVideoView.seekTo(mPosition);
            updatePlaybackState();
        }

        @Override
        public void onFastForward() {
            fastForward();
        }

        @Override
        public void onRewind() {
            rewind();
        }
    }

}