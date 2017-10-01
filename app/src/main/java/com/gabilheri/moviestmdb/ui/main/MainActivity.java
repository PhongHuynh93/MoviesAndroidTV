package com.gabilheri.moviestmdb.ui.main;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;
import com.gabilheri.moviestmdb.ui.base.NavigationInterface;
import com.gabilheri.moviestmdb.ui.onboard.OnboardingFragment;

import javax.inject.Inject;


/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/8/16.
 */

public class MainActivity extends BaseTvActivity {
    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).appComponent().inject(this);
        NavigationInterface navigationInterface = (NavigationInterface) getParent();
        if (!mSharedPreferences.getBoolean(OnboardingFragment.COMPLETED_ONBOARDING, false)) {
            // This is the first time running the app, let's go to onboarding
            navigationInterface.goToOnBoardScreen();
        }
    }

    @Override
    public Fragment getFragment() {
        return MainFragment.newInstance();
    }
}
