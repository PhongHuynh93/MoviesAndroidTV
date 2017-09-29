package com.gabilheri.moviestmdb.ui.moresample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;

import com.example.myapplication.Movie;
import com.example.myapplication.MovieResponse;
import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.dagger.modules.FragmentModule;
import com.gabilheri.moviestmdb.presenter.ListMoviePresenter;
import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;
import com.gabilheri.moviestmdb.ui.base.GlideBackgroundManager;
import com.gabilheri.moviestmdb.ui.main.ListMovieView;
import com.gabilheri.moviestmdb.ui.presenter.MoviePresenter;

import javax.inject.Inject;

import static com.example.myapplication.util.Constant.NOW_PLAYING;

/**
 * Created by CPU11112-local on 9/29/2017.
 * watch the tutorial here
 * <a href="http://corochann.com/verticalgridfragment-android-tv-application-hands-on-tutorial-19-718.html"></a>
 */
public class VerticalGridFragment extends android.support.v17.leanback.app.VerticalGridFragment implements ListMovieView {
    @Inject
    ListMoviePresenter mListMoviePresenter;

    private static final int NUM_COLUMNS = 5;

    private ArrayObjectAdapter mAdapter;
    private GlideBackgroundManager mBackgroundManager;

    public static Fragment instance() {
        return new VerticalGridFragment();
    }

    // step - must set the presenter in oncreate, if not - have the runtime exception
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().appComponent().newSubFragmentComponent(new FragmentModule(this)).inject(this);
        mListMoviePresenter.attachView(this);

        // title in the top right
        setTitle("List of movies");
//        setBadgeDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.banner));
        prepareBackgroundManager();
        setupFragment();
        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        mListMoviePresenter.detachView();
        mBackgroundManager = null;
        super.onDestroy();
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private void prepareBackgroundManager() {
        // The background manager allows us to manage a dimmed background that does not interfere with the rows
        // It is the preferred way to set the background of a fragment
        mBackgroundManager = new GlideBackgroundManager(getActivity());
    }

    private void setupFragment() {
        // step - must set the presenter here
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mListMoviePresenter.getDataFromServer(NOW_PLAYING, "1");

    }

    @Override
    public void showData(int id, MovieResponse movieResponse) {
        mAdapter = new ArrayObjectAdapter(new MoviePresenter());
        for (Movie movie : movieResponse.getResults()) {
            mAdapter.add(movie);
        }
        setAdapter(mAdapter);
    }

    @Override
    public void showLoadingIndicator(int tag) {

    }

    @Override
    public void hideLoadingIndicator(int tag) {

    }

    @Override
    public void showTryAgainLayout(int tag) {

    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            ((BaseTvActivity) getActivity()).changeBackground(mBackgroundManager, item);
        }
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            ((BaseTvActivity) getActivity()).goToDetailMovie(itemViewHolder, item);
        }
    }
}
