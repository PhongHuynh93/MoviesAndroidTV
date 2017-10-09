package com.gabilheri.moviestmdb.ui.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.support.v7.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.Movie;
import com.example.myapplication.MovieResponse;
import com.example.myapplication.data.local.TheMovieDbAPI;
import com.example.myapplication.data.models.CreditsResponse;
import com.example.myapplication.data.models.MovieDetails;
import com.example.myapplication.data.models.PaletteColors;
import com.example.myapplication.module.HttpClientModule;
import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.dagger.modules.FragmentModule;
import com.gabilheri.moviestmdb.presenter.DetailMoviePresenter;
import com.gabilheri.moviestmdb.ui.playbackWithMediaSession.PlaybackOverlayActivity;
import com.gabilheri.moviestmdb.ui.presenter.MoviePresenter;
import com.gabilheri.moviestmdb.util.CustomMovieDetailPresenter;
import com.gabilheri.moviestmdb.util.PaletteUtils;
import com.gabilheri.moviestmdb.util.PersonPresenter;

import javax.inject.Inject;

import static com.gabilheri.moviestmdb.util.Constant.ACTION_BUY;
import static com.gabilheri.moviestmdb.util.Constant.ACTION_WATCH_TRAILER;

/**
 * Created by CPU11112-local on 9/15/2017.
 */

public class MovieDetailsFragment extends DetailsFragment implements Palette.PaletteAsyncListener, MovieDetailView {
    public static String TRANSITION_NAME = "poster_transition";
    @Inject
    DetailMoviePresenter mDetailMoviePresenter;
    // Add the adapter and use the newly created Presenter to define how to render the objects
    // : 9/16/2017 remember adapter + presenter to know about the how to show and bind datas.
    ArrayObjectAdapter mCastAdapter;
    ArrayObjectAdapter mRecommendationsAdapter;
    // Injects the API using Dagger
    @Inject
    TheMovieDbAPI mDbAPI;
    private DetailsOverviewRow mDetailsOverviewRow;
    private Movie movie;
    private MovieDetails movieDetails;
    private ArrayObjectAdapter mAdapter;
    private CustomMovieDetailPresenter mFullWidthMovieDetailsPresenter;
    private SimpleTarget<GlideDrawable> mGlideDrawableSimpleTarget = new SimpleTarget<GlideDrawable>() {
        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            mDetailsOverviewRow.setImageDrawable(resource);
        }
    };

    /**
     * Creates a new instance of a MovieDetailsFragment
     *
     * @param movie The movie to be used by this fragment
     * @return A newly created instance of MovieDetailsFragment
     */
    public static MovieDetailsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(Movie.class.getSimpleName(), movie);
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Injects this into the main component. Necessary for Dagger 2
        App.instance().appComponent().newSubFragmentComponent(new FragmentModule(this)).inject(this);
        mDetailMoviePresenter.attachView(this);

        if (getArguments() == null || !getArguments().containsKey(Movie.class.getSimpleName())) {
            throw new RuntimeException("An movie is necessary for MovieDetailsFragment");
        }

        // Retrieves the movie from the arguments
        movie = getArguments().getParcelable(Movie.class.getSimpleName());
        setUpAdapter();
        mDetailMoviePresenter.fetchMovieDetails(movie.getId());

        // Adds the adapter and fetches the data
        setupCastMembers();
        setupRecommendationsRow();
    }

    private void setupCastMembers() {
        mDetailMoviePresenter.fetchCastMembers(movie.getId());
    }

    private void setupRecommendationsRow() {
        mDetailMoviePresenter.fetchRecommendations(movie.getId());
    }

    @Override
    public void bindCastMembers(CreditsResponse response) {
        mCastAdapter.addAll(0, response.getCast());
    }

    @Override
    public void bindRecommendations(MovieResponse response) {
        mRecommendationsAdapter.addAll(0, response.getResults());
    }

    // info - this item can be on the constructor or in the set method
    @Override
    public void bindMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
        // Bind the details to the row
        mDetailsOverviewRow.setItem(this.movieDetails);
    }

    /**
     * Sets up the adapter for this Fragment
     * info - FullWidthDetailsOverviewRowPresenter is composed of 2 parts:
     * info - MovieDetailsDescriptionPresenter: Handles the presentation of the details of a movie.
     * info - DetailsOverviewLogoPresenter: Handles the presentation of the poster of a movie. The good part of using this is that along the FullWidthDetailsOverviewSharedElementHelper it will automatically handle the transition of the movie poster from the MainFragment into MovieDetailsFragment.
     */
    private void setUpAdapter() {
        // info - detail movie, MovieDetailsDescriptionPresenter: how to show the detail movie -> make custom class here
        // info - DetailsOverviewLogoPresenter - how to show the logo picture
        mFullWidthMovieDetailsPresenter = new CustomMovieDetailPresenter(new MovieDetailsDescriptionPresenter(),
                new DetailsOverviewLogoPresenter());
        // - FullWidthMovieDetailsPresenter has the method to change the background but happen in compile time not at runtime
        // Handle the transition, the Helper is mainly used because the ActivityTransition is being passed from
        // The Activity into the Fragment
        FullWidthDetailsOverviewSharedElementHelper helper = new FullWidthDetailsOverviewSharedElementHelper();
        helper.setSharedElementEnterTransition(getActivity(), TRANSITION_NAME); // the transition name is important
        mFullWidthMovieDetailsPresenter.setListener(helper); // Attach the listener
        // Define if this element is participating in the transition or not
        mFullWidthMovieDetailsPresenter.setParticipatingEntranceTransition(false);

        mFullWidthMovieDetailsPresenter.setOnActionClickedListener(action -> {
            long id = action.getId();
            if (id == ACTION_WATCH_TRAILER) {
                Intent intent = new Intent(getActivity(), PlaybackOverlayActivity.class);
                intent.putExtra(MovieDetailsActivity.MOVIE, movie);
                startActivity(intent);
            }
        });

        // info - the big adapter need the big presenter with 2 screen
        // info - why we have ClassPresenterSelector, because in this screen, we have 2 levels of screen, not like in MovieFragment (always contains list)
        ClassPresenterSelector classPresenterSelector = new ClassPresenterSelector();
        classPresenterSelector.addClassPresenter(DetailsOverviewRow.class, mFullWidthMovieDetailsPresenter);
        classPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());

        // info - the big adapter for screen
        mAdapter = new ArrayObjectAdapter(classPresenterSelector);

        // info - detail overview
        mDetailsOverviewRow = new DetailsOverviewRow(new MovieDetails());
        mCastAdapter = new ArrayObjectAdapter(new PersonPresenter());
        mRecommendationsAdapter = new ArrayObjectAdapter(new MoviePresenter());

        mAdapter.add(mDetailsOverviewRow);
        // not need headeritem here
        mAdapter.add(new ListRow(mCastAdapter));
        mAdapter.add(new ListRow(mRecommendationsAdapter));

        loadImage(HttpClientModule.POSTER_URL + movie.getPosterPath());
        SparseArrayObjectAdapter listActionNew = new SparseArrayObjectAdapter();
        listActionNew.set(ACTION_WATCH_TRAILER, new Action(ACTION_WATCH_TRAILER, getResources().getString(
                R.string.watch_trailer_1), getResources().getString(R.string.watch_trailer_2)));
        listActionNew.set(ACTION_BUY, new Action(ACTION_BUY, "BUY", "$100"));
        mDetailsOverviewRow.setActionsAdapter(listActionNew);

        // Sets the adapter to the fragment
        setAdapter(mAdapter);
    }

    /**
     * Loads the poster image into the DetailsOverviewRow
     * info - after getting the bitmap, set the palette color to the fullwidth
     *
     * @param url The poster URL
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
     *
     * @param bmp The bitmap from which we want to generate the palette
     */
    private void changePalette(Bitmap bmp) {
        Palette.from(bmp).generate(this);
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
        // only notify the detail row (not the list under)
        int index = mAdapter.indexOf(mDetailsOverviewRow);
        mAdapter.notifyArrayItemRangeChanged(index, 1);
    }
}
