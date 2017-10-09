package com.gabilheri.moviestmdb.data.recommendation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.app.recommendation.ContentRecommendation;
import android.support.v7.graphics.Palette;

import com.bumptech.glide.Glide;
import com.example.myapplication.Movie;
import com.example.myapplication.data.models.PaletteColors;
import com.example.myapplication.interactor.GetMovieList;
import com.example.myapplication.module.HttpClientModule;
import com.example.myapplication.util.Constant;
import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.playbackWithMediaSession.PlaybackOverlayActivity;
import com.gabilheri.moviestmdb.util.PaletteUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 * Created by CPU11112-local on 10/3/2017.
 */

public class UpdateRecommendationService extends Service {
    private static final int MAX_RECOMMENDATIONS = 3;

    @Inject
    GetMovieList mGetMovieList;

    @Inject
    NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        App.instance().appComponent().inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.e("Retrieving popular posts for recommendations...");
        // step - service not need mainthread so use this method.
        mGetMovieList.executeWorkerThread(new MovieListObserver(Constant.NOW_PLAYING), new GetMovieList.RequestValues(Constant.NOW_PLAYING, "1"));

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    private final class MovieListObserver extends DisposableObserver<GetMovieList.ResponseValue> {
        private final int id;

        public MovieListObserver(int id) {
            this.id = id;
        }

        @Override
        @UiThread
        public void onNext(@io.reactivex.annotations.NonNull GetMovieList.ResponseValue responseValue) {
            handleRecommendations(responseValue.getMovieResponse().getResults());
        }

        // step - when we dont in download the video data
        private void handleRecommendations(List<Movie> movieList) {
            Timber.i("Building recommendations...");
            Resources res = getResources();
            int cardWidth = res.getDimensionPixelSize(R.dimen.card_width);
            int cardHeight = res.getDimensionPixelSize(R.dimen.card_height);

            if (movieList == null || movieList.size() == 0) return;

            if (mNotificationManager == null) {
                mNotificationManager = (NotificationManager) getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);
            }

//            step - the recommendations in the launcher is actually the notification -> so we use the notification builder to make it
            // This will be used to build up an object for your content recommendation that will be
            // shown on the TV home page along with other provider's recommendations.
            final ContentRecommendation.Builder builder = new ContentRecommendation.Builder()
                    .setBadgeIcon(R.mipmap.ic_launcher);

            for (int i = 0; i < movieList.size() && i < MAX_RECOMMENDATIONS; i++) {
                Movie post = movieList.get(i);
                // TODO: 10/4/2017 create palette color here
                builder.setIdTag("Post" + i + 1)
                        // step - the text in line 1
                        .setTitle(post.getTitle())
                        // step - the text in line 2
                        .setText(getString(R.string.browser_header_3))
                        .setProgress(100, 0)
                        .setSortKey("1.0")
                        .setAutoDismiss(true)
                        // step - the color contains 2 texts, use the pallet color
//                        .setColor(ContextCompat.getColor(getApplicationContext(), R.color.primary))
                        .setBackgroundImageUri(HttpClientModule.BACKDROP_URL + post.getBackdropPath())
                        .setGroup("Trending")
                        .setStatus(ContentRecommendation.CONTENT_STATUS_READY)
                        .setContentTypes(new String[]{ContentRecommendation.CONTENT_TYPE_VIDEO})
                        .setContentIntentData(ContentRecommendation.INTENT_TYPE_ACTIVITY,
                                PlaybackOverlayActivity.buildIntent(UpdateRecommendationService.this, post), 0, null);

                try {
                    // use this method in the background thread
                    Bitmap bitmap = Glide.with(getApplication())
                            .load(HttpClientModule.BACKDROP_URL + post.getBackdropPath())
                            .asBitmap()
                            .into(cardWidth, cardHeight) // Only use for synchronous .get()
                            .get();
                    Palette palette = Palette.from(bitmap).generate();
                    PaletteColors colors = PaletteUtils.getPaletteColors(palette);
                    builder.setColor(colors.getToolbarBackgroundColor());
                    builder.setContentImage(bitmap);

                    // Create an object holding all the information used to recommend the content.
                    ContentRecommendation rec = builder.build();
                    Notification notification = rec.getNotificationObject(getApplicationContext());

                    // Recommend the content by publishing the notification.
                    mNotificationManager.notify(i + 1, notification);
                } catch (InterruptedException | ExecutionException e) {
                    Timber.e("Could not create recommendation: %s", e.getMessage());
                }

                // info: 10/5/2017 - remove these because we already define above
//                // Create an object holding all the information used to recommend the content.
//                ContentRecommendation rec = builder.build();
//                Notification notification = rec.getNotificationObject(getApplicationContext());
//
//                // Recommend the content by publishing the notification.
//                mNotificationManager.notify(i + 1, notification);
            }
        }

        @Override
        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }
}
