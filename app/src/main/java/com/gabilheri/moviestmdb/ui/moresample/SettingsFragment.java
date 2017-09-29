package com.gabilheri.moviestmdb.ui.moresample;

import android.app.Fragment;
import android.support.v14.preference.PreferenceFragment;
import android.support.v17.preference.LeanbackSettingsFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by CPU11112-local on 9/29/2017.
 */

public class SettingsFragment extends LeanbackSettingsFragment {
    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onPreferenceStartInitialScreen() {

    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
        return false;
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragment caller, PreferenceScreen pref) {
        return false;
    }
}
