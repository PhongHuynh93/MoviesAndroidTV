package com.gabilheri.moviestmdb.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.dagger.modules.HttpClientModule;
import com.gabilheri.moviestmdb.data.Api.TheMovieDbAPI;
import com.gabilheri.moviestmdb.data.models.Movie;
import com.gabilheri.moviestmdb.data.models.MovieResponse;
import com.gabilheri.moviestmdb.ui.adapter.PaginationAdapter;
import com.gabilheri.moviestmdb.ui.adapter.PostAdapter;
import com.gabilheri.moviestmdb.ui.base.GlideBackgroundManager;
import com.gabilheri.moviestmdb.ui.detail.MovieDetailsActivity;
import com.gabilheri.moviestmdb.ui.detail.MovieDetailsFragment;
import com.gabilheri.moviestmdb.ui.widget.MovieCardView;
import com.gabilheri.moviestmdb.util.Config;
import com.gabilheri.moviestmdb.util.Constant;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.gabilheri.moviestmdb.ui.adapter.PaginationAdapter.KEY_TAG;
import static com.gabilheri.moviestmdb.util.Constant.NOW_PLAYING;
import static com.gabilheri.moviestmdb.util.Constant.POPULAR;
import static com.gabilheri.moviestmdb.util.Constant.SETTING;
import static com.gabilheri.moviestmdb.util.Constant.TOP_RATED;
import static com.gabilheri.moviestmdb.util.Constant.UPCOMING;

/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */

public class MainFragment extends BrowseFragment implements OnItemViewSelectedListener, OnItemViewClickedListener {
    @Inject
    TheMovieDbAPI mDbAPI;

    private GlideBackgroundManager mBackgroundManager;

    SparseArray<MovieRow> mRows;
    // big adapter
    private ArrayObjectAdapter rowsAdapter;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * info - Most of the code we will be adding is inside the createRows() and onActivityCreated() method, that is where we will setup the adapters for the fragment.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        App.instance().appComponent().inject(this);

        prepareBackgroundManager();
        setupUIElements();
        setupEventListeners();

        loadRows();

        prepareEntranceTransition();
    }

    private void loadRows() {
        // FIXME: 9/14/2017 this is related to database, so put it in another file, not put in view
        createDataRows();
        // Creates the RowsAdapter for the Fragment
        // The ListRowPresenter tells to render ListRow objects
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        createTabLayout();
        createRows();
    }

    /**
     * show the search icon
     */
    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });
        // set the click listener
        setOnItemViewClickedListener(this);
        setOnItemViewSelectedListener(this);
    }

    private void prepareBackgroundManager() {
        // The background manager allows us to manage a dimmed background that does not interfere with the rows
        // It is the preferred way to set the background of a fragment
        mBackgroundManager = new GlideBackgroundManager(getActivity());
    }

    private void setupUIElements() {
        // The brand color will be used as the background for the Headers fragment
        setBrandColor(ContextCompat.getColor(getActivity(), R.color.primary_transparent));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // The TMDB logo on the right corner. It is necessary to show based on their API usage policy
        setBadgeDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.powered_by));

        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.accent_color));

        // set icon with header
        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new IconHeaderItemPresenter();
            }
        });
    }

    /**
     * Creates the data rows objects
     * for each row includes 1 presenter + adapter
     */
    private void createDataRows() {
        mRows = new SparseArray<>();

        // info - not need movie presenter because postadapter handle all small presenter inside
//        MoviePresenter moviePresenter = new MoviePresenter();
        mRows.put(NOW_PLAYING, new MovieRow()
                        .setId(NOW_PLAYING)
                        .setAdapter(new PostAdapter(getActivity(), String.valueOf(NOW_PLAYING)))
//                .setTitle("Now Playing")
                        .setPage(1)
        );
        mRows.put(TOP_RATED, new MovieRow()
                        .setId(TOP_RATED)
                        .setAdapter(new PostAdapter(getActivity(), String.valueOf(TOP_RATED)))
//                .setTitle("Top Rated")
                        .setPage(1)
        );
        mRows.put(POPULAR, new MovieRow()
                        .setId(POPULAR)
                        .setAdapter(new PostAdapter(getActivity(), String.valueOf(POPULAR)))
//                .setTitle("Popular")
                        .setPage(1)
        );
        mRows.put(UPCOMING, new MovieRow()
                        .setId(UPCOMING)
                        .setAdapter(new PostAdapter(getActivity(), String.valueOf(UPCOMING)))
//                .setTitle("Upcoming")
                        .setPage(1)
        );
    }

    /**
     * info - important code here - Creates the rows and sets up the adapter of the fragment
     */
    private void createRows() {
        String[] categories = getResources().getStringArray(R.array.categories);

        for (int i = 0; i < mRows.size(); i++) {
            MovieRow row = mRows.get(i);
            // Adds a new ListRow to the adapter. Each row will contain a collection of Movies
            // That will be rendered using the MoviePresenter
            HeaderItem headerItem = new HeaderItem(row.getId(), categories[i]);
            ListRow listRow = new ListRow(headerItem, row.getAdapter());
            rowsAdapter.add(listRow);
            loadData(row.getId(), (PostAdapter) row.getAdapter());
        }

        // Sets this fragments Adapter.
        // The setAdapter method is defined in the BrowseFragment of the Leanback Library
        setAdapter(rowsAdapter);
    }


    private void createTabLayout() {
        HeaderItem gridHeader = new HeaderItem(SETTING, getString(R.string.browser_header_5));

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getString(R.string.recommended));
        gridRowAdapter.add(getString(R.string.trending));
        gridRowAdapter.add(getString(R.string.music));
        gridRowAdapter.add(getString(R.string.comedy));
        rowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));
    }

    private void loadData(@Constant.TypeMovieMode int tag, PostAdapter adapter) {
        if (adapter.shouldShowLoadingIndicator()) adapter.showLoadingIndicator();

        Map<String, String> options = adapter.getAdapterOptions();
        String nextPage = options.get(PaginationAdapter.KEY_NEXT_PAGE);

        Observable<MovieResponse> movieResponseObservable;
        switch (tag) {
            case Constant.NOW_PLAYING:
                movieResponseObservable = mDbAPI.getNowPlayingMovies(Config.API_KEY_URL, nextPage);
                break;
            case Constant.POPULAR:
                movieResponseObservable = mDbAPI.getPopularMovies(Config.API_KEY_URL, nextPage);
                break;
            case Constant.TOP_RATED:
                movieResponseObservable = mDbAPI.getTopRatedMovies(Config.API_KEY_URL, nextPage);
                break;
            case Constant.UPCOMING:
                movieResponseObservable = mDbAPI.getUpcomingMovies(Config.API_KEY_URL, nextPage);
                break;
            case Constant.SETTING:
            default:
                throw new IllegalArgumentException("Not have this argument");
        }
        // depend on tag, load the suitable movie
        movieResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    adapter.removeLoadingIndicator();
                    if (adapter.size() == 0 && response.getResults().isEmpty()) {
                        adapter.showReloadCard();
                    } else {
                        adapter.setNextPage(adapter.getNextPage() + 1);
                        adapter.addAllItems(response.getResults());
                    }

                    bindMovieResponse(response, tag);
                    startEntranceTransition();
                }, e -> {
                    adapter.removeLoadingIndicator();
                    if (adapter.size() == 0) {
                        adapter.showTryAgainCard();
                    } else {
                        Toast.makeText(
                                getActivity(),
                                getString(R.string.error_message_loading_more_posts),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    Timber.e(e, "Error fetching now playing movies: %s", e.getMessage());
                });
    }

    /**
     * Binds a movie response to it's adapter
     *
     * @param response The response from TMDB API
     * @param id       The ID / position of the row
     */
    // TODO: 9/14/2017 miss endless loading
    private void bindMovieResponse(MovieResponse response, int id) {
        MovieRow row = mRows.get(id);
        row.setPage(row.getPage() + 1);
        for (Movie m : response.getResults()) {
            if (m.getPosterPath() != null) { // Avoid showing movie without posters
                // info - get adapter of eachrow and add this poster
                row.getAdapter().add(m);
            }
        }
    }

    // when clicked -> open another activity to load the picture
    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof Movie) {
            Movie movie = (Movie) item;
            Intent i = new Intent(getActivity(), MovieDetailsActivity.class);
            // Pass the movie to the activity
            i.putExtra(Movie.class.getSimpleName(), movie);

            if (itemViewHolder.view instanceof MovieCardView) {
                // Pass the ImageView to allow a nice transition
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((MovieCardView) itemViewHolder.view).getPosterIV(),
                        MovieDetailsFragment.TRANSITION_NAME).toBundle();
                getActivity().startActivity(i, bundle);
            } else {
                startActivity(i);
            }
        }
    }

    // when selected, load the image of that poster
    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof Movie) {
            Movie movie = (Movie) item;
            // load the movie background
            mBackgroundManager.loadImage(HttpClientModule.BACKDROP_URL + movie.getBackdropPath());
            // info - add the load more
            int index = rowsAdapter.indexOf(row);
            // get the adapter for the suitable row
            PostAdapter adapter =
                    ((PostAdapter) ((ListRow) rowsAdapter.get(index)).getAdapter());

            if (adapter.get(adapter.size() - 1).equals(item) && adapter.shouldLoadNextPage()) {
                loadData(Integer.valueOf(adapter.getAdapterOptions().get(KEY_TAG)), adapter);
            }
        }

    }
}
