package com.gabilheri.moviestmdb.ui.moresample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.support.v4.content.ContextCompat;

import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.presenter.MoviePresenter;

/**
 * Created by CPU11112-local on 9/29/2017.
 */

public class VerticalGridFragment extends android.support.v17.leanback.app.VerticalGridFragment {
    private static final int NUM_COLUMNS = 5;

    private ArrayObjectAdapter mAdapter;

    public static Fragment instance() {
        return new VerticalGridFragment();
    }

    // // TODO: 9/29/2017 show list of movies by retrofit

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // title in the top right
        setTitle("List of movies");
        setBadgeDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.banner));
        setupFragment();
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private void setupFragment() {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mAdapter = new ArrayObjectAdapter(new MoviePresenter());

        /* Add movie items */
//        try {
//            mVideoLists = VideoProvider.buildMedia(getActivity());
//        } catch (JSONException e) {
//            Log.e(TAG, e.toString());
//        }
//        for (int i = 0; i < 3; i++) { // This loop is to for increasing the number of contents. not necessary.
//            for (Map.Entry<String, List<Movie>> entry : mVideoLists.entrySet()) {
//                // String categoryName = entry.getKey();
//                List<Movie> list = entry.getValue();
//                for (int j = 0; j < list.size(); j++) {
//                    Movie movie = list.get(j);
//                    mAdapter.add(movie);
//                }
//            }
//        }
        setAdapter(mAdapter);
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            // write program here
        }
    }
}
