package com.gabilheri.moviestmdb.ui.search;

import android.app.Fragment;
import android.content.Intent;


/**
 * Created by CPU11112-local on 9/20/2017.
 */

public class SearchActivity extends LeanbackActivity {
    private SearchFragment mFragment;

    @Override
    public Fragment getFragment() {
        mFragment = SearchFragment.newInstance();
        return mFragment;
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
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // If there are no results found, press the left key to reselect the microphone
//        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT && !mFragment.hasResults()) {
//            mFragment.focusOnSearch();
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
