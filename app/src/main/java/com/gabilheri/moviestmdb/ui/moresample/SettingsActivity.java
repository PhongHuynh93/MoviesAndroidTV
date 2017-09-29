package com.gabilheri.moviestmdb.ui.moresample;


import android.app.Fragment;

import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;

/**
 * Created by CPU11112-local on 9/29/2017.
 */

public class SettingsActivity extends BaseTvActivity {

    @Override
    public Fragment getFragment() {
        return SettingsFragment.newInstance();
    }
}
