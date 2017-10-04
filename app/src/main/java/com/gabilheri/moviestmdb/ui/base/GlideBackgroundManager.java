package com.gabilheri.moviestmdb.ui.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v17.leanback.app.BackgroundManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;

/**
 * @author Marcus Gabilheri (gabilher)
 * @since 7/21/16
 */

/**
 * info - useful base class to contains the default schedule
 * NOTE: >> DO NOT USE << images with transparency on then. The BackgroundManager freaks out and a really weird
 * stuff happens with the cards.
 */

// TODO: 10/4/2017 used this with life cycle aware to auto clean when we dont need it
public class GlideBackgroundManager implements LifecycleObserver {

    private static final String TAG = GlideBackgroundManager.class.getSimpleName();
    private static final int BACKGROUND_UPDATE_DELAY = 200;
    public static GlideBackgroundManager instance;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private WeakReference<Activity> mActivityWeakReference;
    private BackgroundManager mBackgroundManager;
    private String mBackgroundURI;
    private Timer mBackgroundTimer;
    private SimpleTarget<GlideDrawable> mGlideDrawableSimpleTarget = new SimpleTarget<GlideDrawable>() {
        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            setBackground(resource);
        }
    };

    /**
     * @param activity
     *      The activity to which this WindowManager is attached
     */
    public GlideBackgroundManager(Activity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (activity instanceof LifecycleOwner) {
            ((LifecycleOwner) activity).getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Timber.e("Call on attach");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Timber.e("Call on stop");
        mBackgroundManager.release();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        // Clean up your resources here
        Timber.e("Call on ON_DESTROY");
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
            mBackgroundTimer = null;
        }
        mBackgroundManager = null;
    }

    public void loadImage(String imageUrl) {
        mBackgroundURI = imageUrl;
        startBackgroundTimer();
    }

    public void setBackground(Drawable drawable) {
        if (mBackgroundManager != null) {
            if (!mBackgroundManager.isAttached()) {
                mBackgroundManager.attach(mActivityWeakReference.get().getWindow());
            }
            mBackgroundManager.setDrawable(drawable);
        }
    }

    /**
     * Cancels an ongoing background change
     */
    public void cancelBackgroundChange() {
        mBackgroundURI = null;
        cancelTimer();
    }

    /**
     * Stops the timer
     */
    private void cancelTimer() {
        if (mBackgroundTimer != null) {
            mBackgroundTimer.cancel();
        }
    }

    /**
     * Starts the background change timer
     */
    private void startBackgroundTimer() {
        cancelTimer();
        mBackgroundTimer = new Timer();
        /* set delay time to reduce too much background image loading process */
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    /**
     * Updates the background with the last known URI
     */
    public void updateBackground() {
        if (mActivityWeakReference.get() != null) {
            Glide.with(mActivityWeakReference.get())
                    .load(mBackgroundURI)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mGlideDrawableSimpleTarget);
        }
    }

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(() -> {
                if (mBackgroundURI != null) {
                    updateBackground();
                }
            });
        }
    }

}
