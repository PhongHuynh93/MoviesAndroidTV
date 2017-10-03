package com.gabilheri.moviestmdb.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import com.example.myapplication.data.models.Option;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.presenter.IconItemPresenter;
import com.gabilheri.moviestmdb.ui.presenter.LoadingPresenter;
import com.gabilheri.moviestmdb.ui.widget.LoadingCardView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 9/17/2017.
 */

/**
 * info -this adapter add the paging here
 * 4 card:
 * simple ImageViewCard
 * loading card
 * If there are no cards currently loaded and the request returns an error, then a “Try Again” card will be displayed so the user can re-attempt the request.
 * If there are no cards currently loaded and there are no results returned, then a “Check Again” card will be displayed so the user can make the request to check again.
 */
public abstract class PaginationAdapter extends ArrayObjectAdapter {
    public static final String KEY_TAG = "tag";
    public static final String KEY_ANCHOR = "anchor";
    public static final String KEY_NEXT_PAGE = "next_page";

    private Context mContext;
    private Integer mNextPage;
    private LoadingPresenter mLoadingPresenter;
    private IconItemPresenter mIconItemPresenter;
    private Presenter mPresenter;

    private String mRowTag;
    private String mAnchor;
    private int mLoadingIndicatorPosition;

    /**
     * @param context
     * @param presenter is the MoviePresenter
     * @param tag
     */
    public PaginationAdapter(Context context, Presenter presenter, String tag) {
        mContext = context;
        mPresenter = presenter;
        mLoadingPresenter = new LoadingPresenter();
        mIconItemPresenter = new IconItemPresenter();
        mLoadingIndicatorPosition = -1;
        mNextPage = 1;
        mRowTag = tag;
        setPresenterSelector();
    }

    public void setTag(String tag) {
        mRowTag = tag;
    }

    public int getNextPage() {
        return mNextPage;
    }

    public void setNextPage(int page) {
        mNextPage = page;
    }

    // step - we can add multiple view into one adapter, this adapter use this method setPresenterSelector() from the super class to suitable instant the presenter
    public void setPresenterSelector() {
        setPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                if (item instanceof LoadingCardView) {
                    return mLoadingPresenter;
                } else if (item instanceof Option) {
                    return mIconItemPresenter;
                }
                // is the MoviePresenter
                return mPresenter;
            }
        });
    }

    public List<Object> getItems() {
        return unmodifiableList();
    }

    public boolean shouldShowLoadingIndicator() {
        return mLoadingIndicatorPosition == -1;
    }

    // step - add the loading indicator when firstime load the datas
    public void showLoadingIndicator() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mLoadingIndicatorPosition = size();
                add(mLoadingIndicatorPosition, new LoadingCardView(mContext));
                notifyItemRangeInserted(mLoadingIndicatorPosition, 1);
            }
        });
    }

    // step - remove the loading indicator
    public void removeLoadingIndicator() {
        removeItems(mLoadingIndicatorPosition, 1);
        notifyItemRangeRemoved(mLoadingIndicatorPosition, 1);
        mLoadingIndicatorPosition = -1;
    }

    public void setAnchor(String anchor) {
        mAnchor = anchor;
    }

    public void addPosts(List<?> posts) {
        if (posts.size() > 0) {
            addAll(size(), posts);
        } else {
            mNextPage = 0;
        }
    }

    public boolean shouldLoadNextPage() {
        return shouldShowLoadingIndicator() && mNextPage != 0;
    }

    public Map<String, String> getAdapterOptions() {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_TAG, mRowTag);
        map.put(KEY_ANCHOR, mAnchor);
        map.put(KEY_NEXT_PAGE, String.valueOf(mNextPage.toString()));
        return map;
    }

    public void showReloadCard() {
        Option option = new Option(
                mContext.getString(R.string.title_no_videos),
                mContext.getString(R.string.message_check_again),
                R.drawable.ic_refresh_white);
        add(option);
    }

    public void showTryAgainCard() {
        Option option = new Option(
                mContext.getString(R.string.title_oops),
                mContext.getString(R.string.message_try_again),
                R.drawable.ic_refresh_white);
        add(option);
    }

    public void removeReloadCard() {
        if (isRefreshCardDisplayed()) {
            removeItems(0, 1);
            notifyItemRangeRemoved(size(), 1);
        }
    }

    public boolean isRefreshCardDisplayed() {
        Object item = get(size() - 1);
        if (item instanceof Option) {
            Option option = (Option) item;
            String noVideosTitle = mContext.getString(R.string.title_no_videos);
            String oopsTitle = mContext.getString(R.string.title_oops);
            return (option.title.equals(noVideosTitle) ||
                    option.title.equals(oopsTitle));
        }
        return false;
    }

    public abstract void addAllItems(List<?> items);

    public abstract List<?> getAllItems();


}
