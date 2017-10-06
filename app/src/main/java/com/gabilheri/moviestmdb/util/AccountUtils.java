package com.gabilheri.moviestmdb.util;

import android.content.SharedPreferences;

import static com.gabilheri.moviestmdb.util.Constant.PREF_KEY_ACCESS_TOKEN;

public class AccountUtils {
    private final SharedPreferences mSharePref;

    public AccountUtils(SharedPreferences sharedPreferences) {
        mSharePref = sharedPreferences;
    }

    public boolean isUserAuthenticated() {
        return mSharePref.getString(PREF_KEY_ACCESS_TOKEN, null) != null;

    }

}
