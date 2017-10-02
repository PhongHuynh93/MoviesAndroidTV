package com.gabilheri.moviestmdb.ui.moresample.setting;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v17.preference.LeanbackSettingsFragment;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import com.gabilheri.moviestmdb.R;

/**
 * Created by CPU11112-local on 9/29/2017.
 */

public class SettingsFragment extends LeanbackSettingsFragment implements DialogPreference.TargetFragment {
    private PreferenceFragment mPreferenceFragment;

    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onPreferenceStartInitialScreen() {
        mPreferenceFragment = buildPreferenceFragment(R.xml.settings, null);
        startPreferenceFragment(mPreferenceFragment);
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
        return false;
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragment caller, PreferenceScreen pref) {
        return false;
    }

    @Override
    public Preference findPreference(CharSequence key) {
        return null;
    }

    private PreferenceFragment buildPreferenceFragment(int preferenceResId, String root) {
        PreferenceFragment fragment = new PrefFragment();
        Bundle args = new Bundle();
        args.putInt(PREFERENCE_RESOURCE_ID, preferenceResId);
        args.putString(PREFERENCE_ROOT, root);
        fragment.setArguments(args);
        return fragment;
    }
}
