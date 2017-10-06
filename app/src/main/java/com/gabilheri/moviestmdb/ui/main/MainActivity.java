package com.gabilheri.moviestmdb.ui.main;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.ui.base.BaseTvActivity;
import com.gabilheri.moviestmdb.ui.login.ConnectFragment;
import com.gabilheri.moviestmdb.ui.login.OnClickViewModel;
import com.gabilheri.moviestmdb.ui.onboard.OnboardingFragment;
import com.gabilheri.moviestmdb.util.AccountUtils;

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
    @Inject
    AccountUtils mAccountUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((App) getApplication()).appComponent().inject(this);

        super.onCreate(savedInstanceState);
        if (!mSharedPreferences.getBoolean(OnboardingFragment.COMPLETED_ONBOARDING, false)) {
            // This is the first time running the app, let's go to onboarding
            goToOnBoardScreen();
        }

        OnClickViewModel model = ViewModelProviders.of(this).get(OnClickViewModel.class);
        model.getSelected().observe(this, isClicked -> {
            if (isClicked) {
                addFragment(MainFragment.newInstance());
            }
        });
    }

    @Override
    public Fragment getFragment() {
        if (mAccountUtils.isUserAuthenticated()) {
            return MainFragment.newInstance();
        } else {
            return ConnectFragment.newInstance();
        }
    }
}
