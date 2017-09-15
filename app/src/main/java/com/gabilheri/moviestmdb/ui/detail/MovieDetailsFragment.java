package com.gabilheri.moviestmdb.ui.detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v7.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.dagger.modules.HttpClientModule;
import com.gabilheri.moviestmdb.data.Api.TheMovieDbAPI;
import com.gabilheri.moviestmdb.data.models.Movie;
import com.gabilheri.moviestmdb.data.models.MovieDetails;
import com.gabilheri.moviestmdb.data.models.PaletteColors;
import com.gabilheri.moviestmdb.util.Config;
import com.gabilheri.moviestmdb.util.CustomMovieDetailPresenter;
import com.gabilheri.moviestmdb.util.PaletteUtils;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by CPU11112-local on 9/15/2017.
 */

public class MovieDetailsFragment extends DetailsFragment implements Palette.PaletteAsyncListener {

    public static String TRANSITION_NAME = "poster_transition";

    // Injects the API using Dagger
    @Inject
    TheMovieDbAPI mDbAPI;

    private Movie movie;
    private MovieDetails movieDetails;
    private ArrayObjectAdapter mAdapter;
    private CustomMovieDetailPresenter mFullWidthMovieDetailsPresenter;
    private DetailsOverviewRow mDetailsOverviewRow;

    /**
     * Creates a new instance of a MovieDetailsFragment
     * @param movie
     *      The movie to be used by this fragment
     * @return
     *      A newly created instance of MovieDetailsFragment
     */
    public static MovieDetailsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(Movie.class.getSimpleName(), movie);
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injects this into the main component. Necessary for Dagger 2
        App.instance().appComponent().inject(this);
        if (getArguments() == null || !getArguments().containsKey(Movie.class.getSimpleName())) {
            throw new RuntimeException("An movie is necessary for MovieDetailsFragment");
        }

        // Retrieves the movie from the arguments
        movie = getArguments().getParcelable(Movie.class.getSimpleName());
        setUpAdapter();
        setUpDetailsOverviewRow();
    }

    /**
     * Sets up the adapter for this Fragment
     * info - FullWidthDetailsOverviewRowPresenter is composed of 2 parts:
     * info - MovieDetailsDescriptionPresenter: Handles the presentation of the details of a movie.
     * info - DetailsOverviewLogoPresenter: Handles the presentation of the poster of a movie. The good part of using this is that along the FullWidthDetailsOverviewSharedElementHelper it will automatically handle the transition of the movie poster from the MainFragment into MovieDetailsFragment.
     */
    private void setUpAdapter() {
        // Create the FullWidthPresenter
        mFullWidthMovieDetailsPresenter = new CustomMovieDetailPresenter(new MovieDetailsDescriptionPresenter(),
                new DetailsOverviewLogoPresenter());

        // Handle the transition, the Helper is mainly used because the ActivityTransition is being passed from
        // The Activity into the Fragment
        FullWidthDetailsOverviewSharedElementHelper helper = new FullWidthDetailsOverviewSharedElementHelper();
        helper.setSharedElementEnterTransition(getActivity(), TRANSITION_NAME); // the transition name is important
        mFullWidthMovieDetailsPresenter.setListener(helper); // Attach the listener
        // Define if this element is participating in the transition or not
        mFullWidthMovieDetailsPresenter.setParticipatingEntranceTransition(false);

        // Class presenter selector allows the Adapter to render Rows and the details
        // It can be used in any of the Adapters by the Leanback library
        ClassPresenterSelector classPresenterSelector = new ClassPresenterSelector();
        classPresenterSelector.addClassPresenter(DetailsOverviewRow.class, mFullWidthMovieDetailsPresenter);
        classPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
        mAdapter = new ArrayObjectAdapter(classPresenterSelector);
        // Sets the adapter to the fragment
        setAdapter(mAdapter);
    }

    /**
     * Sets up the details overview rows
     */
    private void setUpDetailsOverviewRow() {
        mDetailsOverviewRow = new DetailsOverviewRow(new MovieDetails());
        mAdapter.add(mDetailsOverviewRow);
        loadImage(HttpClientModule.POSTER_URL + movie.getPosterPath());
        fetchMovieDetails();
    }

    private SimpleTarget<GlideDrawable> mGlideDrawableSimpleTarget = new SimpleTarget<GlideDrawable>() {
        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            mDetailsOverviewRow.setImageDrawable(resource);
        }
    };

    /**
     * Loads the poster image into the DetailsOverviewRow
     * @param url
     *      The poster URL
     */
    private void loadImage(String url) {
        Glide.with(getActivity())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // Add this in order to generate the Palette
                        changePalette(((GlideBitmapDrawable) resource).getBitmap());
                        return false;
                    }

                })
                .into(mGlideDrawableSimpleTarget);
    }

    /**
     * Generates a palette from a Bitmap
     * @param bmp
     *      The bitmap from which we want to generate the palette
     */
    private void changePalette(Bitmap bmp) {
        Palette.from(bmp).generate(this);
    }


    /**
     * Fetches the movie details for a specific Movie.
     */
    private void fetchMovieDetails() {
        mDbAPI.getMovieDetails(movie.getId(), Config.API_KEY_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindMovieDetails, e -> {
                    Timber.e(e, "Error fetching data: %s", e.getMessage());
                });
    }

    private void bindMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
        // Bind the details to the row
        mDetailsOverviewRow.setItem(this.movieDetails);
    }

    @Override
    public void onGenerated(Palette palette) {
        PaletteColors colors = PaletteUtils.getPaletteColors(palette);
        mFullWidthMovieDetailsPresenter.setActionsBackgroundColor(colors.getStatusBarColor());
        mFullWidthMovieDetailsPresenter.setBackgroundColor(colors.getToolbarBackgroundColor());

        // info - if we run this code, you may notice that the color still not changing!
        /**
         * So… what is the deal?
         * Since everything in the Fragment is based of rows with a ViewHolder, we need to notify that the item has changed in order for onBindRowViewHolder be called again.
         * So… what is the deal? Since everything in the Fragment is based of rows with a ViewHolder, we need to notify that the item has changed in order for onBindRowViewHolder be called again.
         */
        if (movieDetails != null) {
            this.movieDetails.setPaletteColors(colors);
        }
        notifyDetailsChanged();
    }

    private void notifyDetailsChanged() {
        mDetailsOverviewRow.setItem(this.movieDetails);
        int index = mAdapter.indexOf(mDetailsOverviewRow);
        mAdapter.notifyArrayItemRangeChanged(index, 1);
    }
}
