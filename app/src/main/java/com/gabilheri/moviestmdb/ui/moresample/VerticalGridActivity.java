package com.gabilheri.moviestmdb.ui.moresample;

import android.app.Fragment;

import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;

/**
 * Created by CPU11112-local on 9/29/2017.
 */
// // FIXME: 9/29/2017 not need to extend basetvactivity to check network to show the errorfragment
public class VerticalGridActivity extends BaseTvActivity {
    @Override
    public Fragment getFragment() {
        return VerticalGridFragment.instance();
    }
}
