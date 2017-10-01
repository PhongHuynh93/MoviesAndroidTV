package com.gabilheri.moviestmdb.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.ErrorFragment;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.example.myapplication.Movie;
import com.example.myapplication.module.HttpClientModule;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.detail.MovieDetailsActivity;
import com.gabilheri.moviestmdb.ui.detail.MovieDetailsFragment;
import com.gabilheri.moviestmdb.ui.moresample.BrowseErrorFragment;
import com.gabilheri.moviestmdb.ui.moresample.SettingsActivity;
import com.gabilheri.moviestmdb.ui.moresample.VerticalGridActivity;
import com.gabilheri.moviestmdb.ui.moresample.guide.GuidedStepActivity;
import com.gabilheri.moviestmdb.ui.onboard.OnboardingActivity;
import com.gabilheri.moviestmdb.ui.widget.MovieCardView;
import com.gabilheri.moviestmdb.util.NetworkUtil;


/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */

public abstract class BaseTvActivity extends Activity implements ControlFragInterface, NavigationInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        addFrag();

    }

    protected void addFrag() {
        Fragment fragment;
        if (NetworkUtil.isNetworkConnected(this)) {
            fragment = getFragment();
        } else {
            fragment = buildErrorFragment();
        }
        addFragment(fragment);
    }


    public void addFragment(Fragment fragment) {
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

            if (itemViewHolder.view instanceof MovieCardView) {
                // Pass the ImageView to allow a nice transition
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        ((MovieCardView) itemViewHolder.view).getPosterIV(),
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
        Intent intent = new Intent(this, GuidedStepActivity.class);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                        .toBundle();
        startActivity(intent, bundle);
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

    public abstract Fragment getFragment();
}
