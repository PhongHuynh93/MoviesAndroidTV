package com.gabilheri.moviestmdb.ui.moresample.authen;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.app.GuidedStepFragment;

import com.gabilheri.moviestmdb.ui.moresample.guide.FirstStepFragment;

/**
 * Created by CPU11112-local on 10/2/2017.
 */

public class AuthenticationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            GuidedStepFragment.addAsRoot(this, new FirstStepAuthenFragment(), android.R.id.content);
        }
    }
}
