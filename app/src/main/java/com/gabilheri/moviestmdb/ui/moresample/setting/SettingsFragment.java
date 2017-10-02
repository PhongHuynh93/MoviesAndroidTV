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
    private final static String PREFERENCE_RESOURCE_ID = "preferenceResource";
    private final static String PREFERENCE_ROOT = "root";

    private PreferenceFragment mPreferenceFragment;

    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    /**
     * step - Called to instantiate the initial PreferenceFragment to be shown in this fragment. Implementations are expected to call startPreferenceFragment(android.app.Fragment).
     */
    @Override
    public void onPreferenceStartInitialScreen() {
        // pass the xml
        mPreferenceFragment = buildPreferenceFragment(R.xml.settings, null);
        startPreferenceFragment(mPreferenceFragment);
    }

    /**
     * Called when the user has clicked on a Preference that has a fragment class name associated with it. The implementation should instantiate and switch to an instance of the given fragment.
     * @param caller
     * @param pref
     * @return
     */
    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
        return false;
    }

    /**
     * Called when the user has clicked on a PreferenceScreen item in order to navigate to a new screen of preferences.
     * @param caller  The fragment requesting navigation.
     * @param pref  The preference screen to navigate to.
     * @return
     */
    @Override
    public boolean onPreferenceStartScreen(PreferenceFragment caller, PreferenceScreen pref) {
        PreferenceFragment frag = buildPreferenceFragment(R.xml.settings, pref.getKey());
        startPreferenceFragment(frag);
        return true;
    }

    // FIXME: 10/2/2017 why has this method
    @Override
    public Preference findPreference(CharSequence key) {
        return mPreferenceFragment.findPreference(key);
    }

    /**
     * we didn't use root here
     * @param preferenceResId
     * @param root
     * @return
     */
    private PreferenceFragment buildPreferenceFragment(int preferenceResId, String root) {
        PreferenceFragment fragment = new PrefFragment();
        Bundle args = new Bundle();
        args.putInt(PREFERENCE_RESOURCE_ID, preferenceResId);
        args.putString(PREFERENCE_ROOT, root);
        fragment.setArguments(args);
        return fragment;
    }
}
