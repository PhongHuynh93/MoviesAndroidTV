package com.gabilheri.moviestmdb.ui.login;

import android.app.Fragment;

import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;

public class ConnectActivity extends BaseTvActivity {

    @Override
    public Fragment getFragment() {
        return ConnectFragment.newInstance();
    }
}
