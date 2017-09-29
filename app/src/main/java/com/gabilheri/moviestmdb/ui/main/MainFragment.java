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

import com.example.myapplication.Movie;
import com.example.myapplication.MovieResponse;
import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.dagger.modules.FragmentModule;
import com.gabilheri.moviestmdb.presenter.ListMoviePresenter;
import com.gabilheri.moviestmdb.ui.adapter.PaginationAdapter;
import com.gabilheri.moviestmdb.ui.adapter.PostAdapter;
import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;
import com.gabilheri.moviestmdb.ui.base.GlideBackgroundManager;
import com.gabilheri.moviestmdb.ui.presenter.GridItemPresenter;
import com.gabilheri.moviestmdb.ui.presenter.GridItemPresenter2;
import com.gabilheri.moviestmdb.ui.presenter.IconHeaderItemPresenter;
import com.gabilheri.moviestmdb.ui.search.SearchActivity;

import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.myapplication.util.Constant.MORE_SAMPLE;
import static com.example.myapplication.util.Constant.NOW_PLAYING;
import static com.example.myapplication.util.Constant.POPULAR;
import static com.example.myapplication.util.Constant.SETTING;
import static com.example.myapplication.util.Constant.TOP_RATED;
import static com.example.myapplication.util.Constant.UPCOMING;
import static com.gabilheri.moviestmdb.ui.adapter.PaginationAdapter.KEY_TAG;

/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */

public class MainFragment extends BrowseFragment implements OnItemViewSelectedListener, OnItemViewClickedListener, ListMovieView {
    @Inject
    ListMoviePresenter mListMoviePresenter;

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
        // inject dagger
        App.instance().appComponent().newSubFragmentComponent(new FragmentModule(this)).inject(this);
        mListMoviePresenter.attachView(this);
        prepareBackgroundManager();
        setupUIElements();
        setupEventListeners();

        loadRows();

        prepareEntranceTransition();
    }


    @Override
    public void onDestroy() {
        mListMoviePresenter.detachView();
        mBackgroundManager = null;
        super.onDestroy();
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
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
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
        mRows.put(NOW_PLAYING, new MovieRow()
                .setId(NOW_PLAYING)
                .setAdapter(new PostAdapter(getActivity(), String.valueOf(NOW_PLAYING)))
                .setPage(1)
        );
        mRows.put(TOP_RATED, new MovieRow()
                .setId(TOP_RATED)
                .setAdapter(new PostAdapter(getActivity(), String.valueOf(TOP_RATED)))
                .setPage(1)
        );
        mRows.put(POPULAR, new MovieRow()
                .setId(POPULAR)
                .setAdapter(new PostAdapter(getActivity(), String.valueOf(POPULAR)))
                .setPage(1)
        );
        mRows.put(UPCOMING, new MovieRow()
                .setId(UPCOMING)
                .setAdapter(new PostAdapter(getActivity(), String.valueOf(UPCOMING)))
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

        // add row with special case
        HeaderItem gridHeader = new HeaderItem(MORE_SAMPLE, getString(R.string.browser_header_6));
        GridItemPresenter2 gridPresenter = new GridItemPresenter2();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(gridPresenter);
        gridRowAdapter.add(getString(R.string.grid_view));
        gridRowAdapter.add(getString(R.string.guidedstep_first_title));
        gridRowAdapter.add(getString(R.string.error_fragment));
        gridRowAdapter.add(getString(R.string.personal_settings));
        ListRow row = new ListRow(gridHeader, gridRowAdapter);
        rowsAdapter.add(row);

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

    private void loadData(int tag, PostAdapter adapter) {
        Map<String, String> options = adapter.getAdapterOptions();
        String nextPage = options.get(PaginationAdapter.KEY_NEXT_PAGE);
        mListMoviePresenter.getDataFromServer(tag, nextPage);
    }

    @Override
    public void showData(int tag, MovieResponse response) {
        PostAdapter adapter = getAdapterDependOnId(tag);

        if (adapter.size() == 0 && response.getResults().isEmpty()) {
            adapter.showReloadCard();
        } else {
            adapter.setNextPage(adapter.getNextPage() + 1);
            adapter.addAllItems(response.getResults());
        }

        bindMovieResponse(response, tag);
        startEntranceTransition();
    }

    @Override
    public void showLoadingIndicator(int tag) {
        PostAdapter adapter = getAdapterDependOnId(tag);
        if (adapter.shouldShowLoadingIndicator()) adapter.showLoadingIndicator();
    }

    @Override
    public void hideLoadingIndicator(int tag) {
        PostAdapter adapter = getAdapterDependOnId(tag);
        adapter.removeLoadingIndicator();
    }

    @Override
    public void showTryAgainLayout(int tag) {
        PostAdapter adapter = getAdapterDependOnId(tag);
        if (adapter.size() == 0) {
            adapter.showTryAgainCard();
        } else {
            Toast.makeText(
                    getActivity(),
                    getString(R.string.error_message_loading_more_posts),
                    Toast.LENGTH_SHORT
            ).show();
        }
        Timber.e("Error fetching now playing movies");
    }

    private PostAdapter getAdapterDependOnId(int tag) {
        MovieRow row = mRows.get(tag);
        return (PostAdapter) row.getAdapter();
    }

    /**
     * Binds a movie response to it's adapter
     *
     * @param response The response from TMDB API
     * @param id       The ID / position of the row
     */
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
        ((BaseTvActivity) getActivity()).goToDetailMovie(itemViewHolder, item);
    }

    // when selected, load the image of that poster
    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        ((BaseTvActivity) getActivity()).changeBackground(mBackgroundManager, item);

        if (item instanceof Movie) {
            // info - listen load more
            int index = rowsAdapter.indexOf(row);
            // get the adapter for the suitable row
            PostAdapter adapter =
                    ((PostAdapter) ((ListRow) rowsAdapter.get(index)).getAdapter());

            if (adapter.get(adapter.size() - 1).equals(item) && adapter.shouldLoadNextPage()) {
                loadData(Integer.valueOf(adapter.getAdapterOptions().get(KEY_TAG)), adapter);
            }
        } else if (item instanceof String) {
            if (((String) item).contains(getString(R.string.grid_view))) {
                Intent intent = new Intent(getActivity(), VerticalGridActivity.class);
                Bundle bundle =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity())
                                .toBundle();
                startActivity(intent, bundle);
            } else if (((String) item).contains(getString(R.string.guidedstep_first_title))) {
                Intent intent = new Intent(getActivity(), GuidedStepActivity.class);
                Bundle bundle =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity())
                                .toBundle();
                startActivity(intent, bundle);
            } else if (((String) item).contains(getString(R.string.error_fragment))) {
                BrowseErrorFragment errorFragment = new BrowseErrorFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_frame, errorFragment)
                        .addToBackStack(null).commit();
            } else if(((String) item).contains(getString(R.string.personal_settings))) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                Bundle bundle =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity())
                                .toBundle();
                startActivity(intent, bundle);
            } else {
                Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }
}
