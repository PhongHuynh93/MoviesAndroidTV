package com.gabilheri.moviestmdb.ui.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v17.leanback.app.ErrorFragment;
import android.view.View;

import com.gabilheri.moviestmdb.R;
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
//        addFragment(MainFragment.newInstance());
//        addFragment(buildErrorFragment());

        if (NetworkUtil.isNetworkConnected(this)) {
            mBrowseFragment = MainFragment.newInstance();
        } else {
            mBrowseFragment = buildErrorFragment();
        }
    }

    private ErrorFragment buildErrorFragment() {
        ErrorFragment errorFragment = new ErrorFragment();
        errorFragment.setTitle(getString(R.string.text_error_oops_title));
        errorFragment.setMessage(getString(R.string.error_message_network_needed_app));
        errorFragment.setButtonText(getString(R.string.text_close));
        errorFragment.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return errorFragment;
    }
}
