package com.gabilheri.moviestmdb.ui.detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
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
import com.gabilheri.moviestmdb.data.models.CreditsResponse;
import com.gabilheri.moviestmdb.data.models.Movie;
import com.gabilheri.moviestmdb.data.models.MovieDetails;
import com.gabilheri.moviestmdb.data.models.MovieResponse;
import com.gabilheri.moviestmdb.data.models.PaletteColors;
import com.gabilheri.moviestmdb.ui.movies.MoviePresenter;
import com.gabilheri.moviestmdb.util.Config;
import com.gabilheri.moviestmdb.util.CustomMovieDetailPresenter;
import com.gabilheri.moviestmdb.util.PaletteUtils;
import com.gabilheri.moviestmdb.util.PersonPresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by CPU11112-local on 9/15/2017.
 */

public class MovieDetailsFragment extends DetailsFragment implements Palette.PaletteAsyncListener {

    public static String TRANSITION_NAME = "poster_transition";

    // Add the adapter and use the newly created Presenter to define how to render the objects
    // TODO: 9/16/2017 remember adapter + presenter to know about the how to show and bind datas.
    ArrayObjectAdapter mCastAdapter = new ArrayObjectAdapter(new PersonPresenter());
    ArrayObjectAdapter mRecommendationsAdapter = new ArrayObjectAdapter(new MoviePresenter());

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

        // Adds the adapter and fetches the data
        setupCastMembers();
        setupRecommendationsRow();
    }


    private void setupCastMembers() {
        mAdapter.add(new ListRow(new HeaderItem(0, "Cast"), mCastAdapter));
        fetchCastMembers();
    }

    private void fetchCastMembers() {
        mDbAPI.getCredits(movie.getId(), Config.API_KEY_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindCastMembers, e -> {
                    Timber.e(e, "Error fetching data: %s", e.getMessage());
                });
    }


    private void bindCastMembers(CreditsResponse response) {
        mCastAdapter.addAll(0, response.getCast());
    }

    private void setupRecommendationsRow() {
        mAdapter.add(new ListRow(new HeaderItem(2, "Recommendations"), mRecommendationsAdapter));
        fetchRecommendations();
    }

    private void fetchRecommendations() {
        mDbAPI.getRecommendations(movie.getId(), Config.API_KEY_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindRecommendations, e -> {
                    Timber.e(e, "Error fetching recommendations: %s", e.getMessage());
                });
    }

    private void bindRecommendations(MovieResponse response) {
        mRecommendationsAdapter.addAll(0, response.getResults());
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

        // info - FullWidthMovieDetailsPresenter has the method to change the background but happen in compile time not at runtime

        // Handle the transition, the Helper is mainly used because the ActivityTransition is being passed from
        // The Activity into the Fragment
        FullWidthDetailsOverviewSharedElementHelper helper = new FullWidthDetailsOverviewSharedElementHelper();
        helper.setSharedElementEnterTransition(getActivity(), TRANSITION_NAME); // the transition name is important
        mFullWidthMovieDetailsPresenter.setListener(helper); // Attach the listener
        // Define if this element is participating in the transition or not
        mFullWidthMovieDetailsPresenter.setParticipatingEntranceTransition(false);

        // info - the big adapter need the big presenter with 2 screen
        // info - why we have ClassPresenterSelector, because in this screen, we have 2 levels of screen, not like in MovieFragment
        // Class presenter selector allows the Adapter to render Rows and the details
        // It can be used in any of the Adapters by the Leanback library
        ClassPresenterSelector classPresenterSelector = new ClassPresenterSelector();
        classPresenterSelector.addClassPresenter(DetailsOverviewRow.class, mFullWidthMovieDetailsPresenter);
        classPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
        // info - the big adapter for screen
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
     * info - after getting the bitmap, set the palette color to the fullwidth
     * @param url
     *      The poster URL
     *
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

    // info - this item can be on the constructor or in the set method
    private void bindMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
        // Bind the details to the row
        mDetailsOverviewRow.setItem(this.movieDetails);
    }

    @Override
    public void onGenerated(Palette palette) {
        // info - set the background color of the detail view
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

    // info - remember it is jsut adapter, so need to notify the color again
    private void notifyDetailsChanged() {
        mDetailsOverviewRow.setItem(this.movieDetails);
        int index = mAdapter.indexOf(mDetailsOverviewRow);
        mAdapter.notifyArrayItemRangeChanged(index, 1);
    }
}
