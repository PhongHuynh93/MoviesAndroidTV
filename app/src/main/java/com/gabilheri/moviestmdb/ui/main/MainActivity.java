package com.gabilheri.moviestmdb.ui.main;

import android.app.Fragment;
import android.os.Bundle;

import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;
import com.gabilheri.moviestmdb.util.NetworkUtil;


/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */

public class MainActivity extends BaseTvActivity {
    private Fragment mBrowseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetworkUtil.isNetworkConnected(this)) {
            mBrowseFragment = MainFragment.newInstance();
        } else {
            mBrowseFragment = buildErrorFragment();
        }
        addFragment(mBrowseFragment);
    }
}
