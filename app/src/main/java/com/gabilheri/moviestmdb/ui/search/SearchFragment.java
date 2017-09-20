package com.gabilheri.moviestmdb.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.gabilheri.moviestmdb.R;

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
public class SearchFragment extends android.support.v17.leanback.app.SearchFragment implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider {
    private ArrayObjectAdapter mRowsAdapter;
    // set it to true when we have result
    private boolean mResultsFound = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // this is adapter to show the search results
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        // info - We need to register this SearchResultProvider by using setSearchResultProvider method,  minimum implementation is like this,
        setSearchResultProvider(this);

        // when click a movie
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return null;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean hasResults() {
        return mRowsAdapter.size() > 0 && mResultsFound;
    }

    public void focusOnSearch() {
        getView().findViewById(R.id.lb_search_bar).requestFocus();
    }


    // todo - open detail movie with share element
    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

//            if (item instanceof Video) {
//                Video video = (Video) item;
//                Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
//                intent.putExtra(VideoDetailsActivity.VIDEO, video);
//
//                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        getActivity(),
//                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
//                        VideoDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
//                getActivity().startActivity(intent, bundle);
//            } else {
//                Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
