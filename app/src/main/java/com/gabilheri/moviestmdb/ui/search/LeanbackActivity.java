package com.gabilheri.moviestmdb.ui.search;

import android.content.Intent;

import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;

/**
 * This parent class contains common methods that run in every activity such as search.
 */
public abstract class LeanbackActivity extends BaseTvActivity {
    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }
}
