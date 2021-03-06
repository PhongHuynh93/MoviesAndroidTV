package com.gabilheri.moviestmdb.ui.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.example.myapplication.Movie;
import com.example.myapplication.module.HttpClientModule;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;
import com.gabilheri.moviestmdb.ui.base.GlideBackgroundManager;

/**
 * Created by CPU11112-local on 9/15/2017.
 */

public class MovieDetailsActivity extends BaseTvActivity {
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String MOVIE = "Movie";

    GlideBackgroundManager mBackgroundManager;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareBackgroundManager();
    }

    @Override
    public Fragment getFragment() {
        // Retrieve the movie through the intent
        movie = getIntent().getExtras().getParcelable(Movie.class.getSimpleName());
        return MovieDetailsFragment.newInstance(movie);
    }

    private void prepareBackgroundManager() {

        // Sets the background of the activity to the backdrop of the movie
        mBackgroundManager = new GlideBackgroundManager(this);
        if (movie != null && movie.getBackdropPath() != null) {
            mBackgroundManager.loadImage(HttpClientModule.BACKDROP_URL + movie.getBackdropPath());
        } else {
            mBackgroundManager.setBackground(ContextCompat.getDrawable(this, R.drawable.material_bg));
        }

    }
}
