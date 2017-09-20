package com.gabilheri.moviestmdb.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.gabilheri.moviestmdb.R;

/**
 * Created by CPU11112-local on 9/20/2017.
 */

public class SearchActivity extends LeanbackActivity {
    private SearchFragment mFragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mFragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_fragment);
    }

    // info - Overriding onSearchRequeseted to activate in-app search
    // if not override, When user tries voice input search, onSearchRequested callback is executed and Google’s global contents search will be launched as default.
    // We override onSearchRequested method for both MainActivity & SearchActivity.
    @Override
    public boolean onSearchRequested() {
        if (mFragment.hasResults()) {
            startActivity(new Intent(this, SearchActivity.class));
        } else {
            mFragment.startRecognition();
        }
        return true;
    }

    // focus in the search bar
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // If there are no results found, press the left key to reselect the microphone
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT && !mFragment.hasResults()) {
            mFragment.focusOnSearch();
        }
        return super.onKeyDown(keyCode, event);
    }
}
