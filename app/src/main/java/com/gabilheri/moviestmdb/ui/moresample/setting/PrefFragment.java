package com.gabilheri.moviestmdb.ui.moresample.setting;

import android.os.Bundle;
import android.support.v17.preference.LeanbackPreferenceFragment;
import android.support.v7.preference.Preference;

import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.base.NavigationInterface;

/**
 * Created by CPU11112-local on 10/2/2017.
 */

public class PrefFragment extends LeanbackPreferenceFragment {
    private final static String PREFERENCE_RESOURCE_ID = "preferenceResource";
    private final static String PREFERENCE_ROOT = "root";
    private NavigationInterface mNavigationInterface;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        mNavigationInterface = (NavigationInterface) getActivity();
        String root = getArguments().getString(PREFERENCE_ROOT, null);
        int prefResId = getArguments().getInt(PREFERENCE_RESOURCE_ID);
        if (root == null) {
            addPreferencesFromResource(prefResId);
        } else {
            setPreferencesFromResource(prefResId, root);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(getString(R.string.pref_key_login))) {
            // Open an AuthenticationActivity
            mNavigationInterface.goToAuthenticationScreen();
        }
        return super.onPreferenceTreeClick(preference);
    }
}
