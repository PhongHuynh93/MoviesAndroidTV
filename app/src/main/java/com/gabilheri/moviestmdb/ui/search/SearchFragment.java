package com.gabilheri.moviestmdb.ui.search;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;

import com.example.myapplication.MovieResponse;
import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.dagger.modules.FragmentModule;
import com.gabilheri.moviestmdb.presenter.SearchMoviePresenter;
import com.gabilheri.moviestmdb.ui.adapter.PaginationAdapter;
import com.gabilheri.moviestmdb.ui.adapter.TagAdapter;
import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;
import com.gabilheri.moviestmdb.ui.base.GlideBackgroundManager;
import com.gabilheri.moviestmdb.util.NetworkUtil;
import com.gabilheri.moviestmdb.util.ToastFactory;

import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by CPU11112-local on 9/20/2017.
 */

/*
 * This class demonstrates how to do in-app search
 * http://corochann.com/android-tv-application-hands-on-tutorial-12-287.html
 *
 * info - Customize in-app search – SearchResultProvider
 * SearchResultProvider intereface is an intereface of Leanback library, used to listen search related event.  We need to override 3 methods.
 * step - getResultsAdapter – returns the adapter which includes the search results, to show search results on SearchFragment.
   step - onQueryTextChange – event listener which is called when user changes search query text.
   step - onQueryTextSubmit – event listener which is called when user submitted search query text.
 */
public class SearchFragment extends android.support.v17.leanback.app.SearchFragment implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider, OnItemViewSelectedListener,
        OnItemViewClickedListener,
        SearchView {
    @Inject
    SearchMoviePresenter mPresenter;

    private static final int REQUEST_SPEECH = 0x00000010;

    private ArrayObjectAdapter mRowsAdapter;
    private TagAdapter mSearchResultsAdapter;
    private HeaderItem mResultsHeader;

    // the text which we use to search
    private String mSearchQuery;

    // set it to true when we have result
    private boolean mResultsFound = false;
    private GlideBackgroundManager mBackgroundManager;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
// inject dagger
        App.instance().appComponent().newSubFragmentComponent(new FragmentModule(this)).inject(this);
        mPresenter.attachView(this);
        // this is adapter to show the search results
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        mSearchResultsAdapter = new TagAdapter(getActivity(), "");

        // info - We need to register this SearchResultProvider by using setSearchResultProvider method,  minimum implementation is like this,
        setSearchResultProvider(this);

        prepareBackgroundManager();
        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void setupEventListeners() {
        // when click a movie,
        setOnItemViewClickedListener(this);
        // when select a movie, change the background
        setOnItemViewSelectedListener(this);

        if (!hasPermission(Manifest.permission.RECORD_AUDIO)) {
            setSpeechRecognitionCallback(new SpeechRecognitionCallback() {
                @Override
                public void recognizeSpeech() {
                    try {
                        startActivityForResult(getRecognizerIntent(), REQUEST_SPEECH);
                    } catch (ActivityNotFoundException e) {
                        Timber.e("Cannot find activity for speech recognizer", e);
                    }
                }
            });
        }
    }

    private boolean hasPermission(final String permission) {
        final Context context = getActivity();
        return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(
                permission, context.getPackageName());
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = new GlideBackgroundManager(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SPEECH:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        setSearchQuery(data, false);
                        break;
                    case Activity.RESULT_CANCELED:
                        Timber.i("Recognizer canceled");
                        break;
                }
                break;
        }
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        loadQuery(newQuery);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        loadQuery(query);
        return true;
    }

    //    info search for movie by query key
    private void loadQuery(String query) {
        // only query if not empty and not the same as the previous query
//        if ((mSearchQuery != null && !mSearchQuery.equals(query))
//                && query.trim().length() > 0
//                || (!TextUtils.isEmpty(query) && !query.equals("nil"))) {
            if (NetworkUtil.isNetworkConnected(getActivity())) {
                mSearchQuery = query;
                searchTaggedPosts(query);
            } else {
                ToastFactory.createWifiErrorToast(getActivity()).show();
            }
//        }
    }

    private void searchTaggedPosts(String tag) {
        mSearchResultsAdapter.setTag(tag);
        // remove the old search
        mRowsAdapter.clear();
        // add list of tags with header
        mResultsHeader = new HeaderItem(0, getString(R.string.text_search_results));
        ListRow listRow = new ListRow(mResultsHeader, mSearchResultsAdapter);
        Timber.e(listRow.getId() + "");
        mRowsAdapter.add(listRow);
        performSearch(mSearchResultsAdapter);
    }

    private void performSearch(final PaginationAdapter adapter) {
        adapter.clear();
        Map<String, String> options = adapter.getAdapterOptions();
        String tag = options.get(PaginationAdapter.KEY_TAG);
        mPresenter.searchMovie(tag);
    }

    // todo - implement this from server
    @Override
    public void showData(MovieResponse movieResponse) {
        if (movieResponse.getResults().isEmpty()) {
            mRowsAdapter.clear();
            mResultsHeader = new HeaderItem(0, getString(R.string.text_no_results));
            mRowsAdapter.add(new ListRow(mResultsHeader, mSearchResultsAdapter));
//            mTagSearchAnchor = "";
//            mUserSearchAnchor = "";
        } else {
            mSearchResultsAdapter.addAllItems(movieResponse.getResults());
//            mTagSearchAnchor = dualResponse.tagSearchAnchor;
//            mUserSearchAnchor = dualResponse.userSearchAnchor;
        }
    }

    public boolean hasResults() {
        return mRowsAdapter.size() > 0 && mResultsFound;
    }

    public void focusOnSearch() {
        getView().findViewById(R.id.lb_search_bar).requestFocus();
    }


    // todo - open detail movie with share element
    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        ((BaseTvActivity) getActivity()).goToDetailMovie(itemViewHolder, item);
    }

    // when select, change the background
    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        // info - only if the post model, change to background

    }
}