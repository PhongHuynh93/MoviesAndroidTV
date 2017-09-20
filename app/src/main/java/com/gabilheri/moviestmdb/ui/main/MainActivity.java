package com.gabilheri.moviestmdb.ui.main;

import android.app.Fragment;

import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;


/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */

public class MainActivity extends BaseTvActivity {
    @Override
    public Fragment getFragment() {
        return MainFragment.newInstance();
    }
}
