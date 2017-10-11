package com.gabilheri.moviestmdb.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.ErrorFragment;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.myapplication.Movie;
import com.example.myapplication.module.HttpClientModule;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.data.recommendation.UpdateRecommendationService;
import com.gabilheri.moviestmdb.ui.detail.MovieDetailsActivity;
import com.gabilheri.moviestmdb.ui.detail.MovieDetailsFragment;
import com.gabilheri.moviestmdb.ui.main.MainActivity;
import com.gabilheri.moviestmdb.ui.main.MainFragment;
import com.gabilheri.moviestmdb.ui.moresample.BrowseErrorFragment;
import com.gabilheri.moviestmdb.ui.moresample.SettingsActivity;
import com.gabilheri.moviestmdb.ui.moresample.VerticalGridActivity;
import com.gabilheri.moviestmdb.ui.moresample.authen.AuthenticationActivity;
import com.gabilheri.moviestmdb.ui.moresample.guide.GuidedStepNewActivity;
import com.gabilheri.moviestmdb.ui.onboard.OnboardingActivity;
import com.gabilheri.moviestmdb.ui.widget.videoloop.VideoCardView;
import com.gabilheri.moviestmdb.util.NetworkUtil;

import timber.log.Timber;


/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */
// this activity cannot extend Appcompath activity because if extend, we have to supply it with appcompath theme
public abstract class BaseTvActivity extends FragmentActivity implements ControlFragInterface, NavigationInterface {
    // current fragment
    Fragment fragment;
    private LifeCycleState mLifeCycleState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        addFrag();
        mLifeCycleState = new LifeCycleState(this);
    }

    protected void addFrag() {
        if (NetworkUtil.isNetworkConnected(this)) {
            fragment = getFragment();
        } else {
            fragment = buildErrorFragment();
        }
        addFragment(fragment);
    }

    public boolean isFragmentActive() {
        return fragment instanceof MainFragment &&
                fragment.isAdded() &&
                !fragment.isDetached() &&
                !fragment.isRemoving() &&
                !mLifeCycleState.isStopping();
    }


    public void addFragment(Fragment fragment) {
        this.fragment = fragment;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tv_frame_content, fragment);
        fragmentTransaction.commit();
    }

    // error not have the internet
    public ErrorFragment buildErrorFragment() {
        ErrorFragment errorFragment = new ErrorFragment();
        errorFragment.setTitle(getString(R.string.text_error_oops_title));
        errorFragment.setMessage(getString(R.string.error_message_network_needed_app));
        errorFragment.setButtonText(getString(R.string.text_close));
        errorFragment.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return errorFragment;
    }

    @Override
    public void showErrorFrag() {
        BrowseErrorFragment errorFragment = new BrowseErrorFragment();
        getFragmentManager().beginTransaction().replace(R.id.tv_frame_content, errorFragment)
                .addToBackStack(null).commit();
    }

    public void goToDetailMovie(Presenter.ViewHolder itemViewHolder, Object item) {
        if (item instanceof Movie) {
            Movie movie = (Movie) item;
            Intent i = new Intent(this, MovieDetailsActivity.class);
            // Pass the movie to the activity
            i.putExtra(Movie.class.getSimpleName(), movie);

            if (itemViewHolder.view instanceof VideoCardView) {
                // Pass the ImageView to allow a nice transition
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        ((VideoCardView) itemViewHolder.view).getPreviewCard(),
                        MovieDetailsFragment.TRANSITION_NAME).toBundle();
                startActivity(i, bundle);
            } else {
                startActivity(i);
            }
        }
    }

    public void changeBackground(GlideBackgroundManager mBackgroundManager, Object item) {
        if (item instanceof Movie) {
            Movie movie = (Movie) item;
            // load the movie background
            mBackgroundManager.loadImage(HttpClientModule.BACKDROP_URL + movie.getBackdropPath());
        }
    }

    @Override
    public void goToGuideScreen() {
        Intent intent = new Intent(this, GuidedStepNewActivity.class);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                        .toBundle();
        startActivity(intent, bundle);
    }

    @Override
    public void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                .toBundle();
        startActivity(intent, bundle);
    }

    @Override
    public void goToMainScreenMusic() {
//         make login via spotify
    }

    @Override
    public void goToSettingScreen() {
        Intent intent = new Intent(this, SettingsActivity.class);
        Bundle bundle =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                        .toBundle();
        startActivity(intent, bundle);
    }

    @Override
    public void goToVerticalScreen() {
        Intent intent = new Intent(this, VerticalGridActivity.class);
        Bundle bundle =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                        .toBundle();
        startActivity(intent, bundle);
    }

    @Override
    public void goToOnBoardScreen() {
        startActivity(new Intent(this, OnboardingActivity.class));
    }

    @Override
    public void goToAuthenticationScreen() {
        startActivity(new Intent(this, AuthenticationActivity.class));
    }

    @Override
    public void runRecommandationService() {
        Intent recommendationIntent = new Intent(this, UpdateRecommendationService.class);
        startService(recommendationIntent);
    }

    public abstract Fragment getFragment();

    public static final class LifeCycleState implements LifecycleObserver {
        private boolean mIsStopping;

        public LifeCycleState(Activity activity) {
            if (activity instanceof LifecycleOwner) {
                ((LifecycleOwner) activity).getLifecycle().addObserver(LifeCycleState.this);
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            mIsStopping = false;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop() {
            mIsStopping = true;
        }

        public boolean isStopping() {
            return mIsStopping;
        }
    }
}
