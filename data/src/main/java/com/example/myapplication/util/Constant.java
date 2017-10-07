package com.example.myapplication.util;

import android.support.annotation.IntDef;

/**
 * Created by CPU11112-local on 9/19/2017.
 */

public class Constant {
    @IntDef({NOW_PLAYING, TOP_RATED, POPULAR, UPCOMING, SETTING})
    public @interface TypeMovieMode {
    }

    public static final int NOW_PLAYING = 0;
    public static final int TOP_RATED = 1;
    public static final int POPULAR = 2;
    public static final int UPCOMING = 3;
    public static final int SETTING = 4;
    public static final int MORE_SAMPLE = 5;
    public static final int OPTION = 6;

    public static final String API_KEY_URL = "53470b56a60668274e1dd9f84d882564";

}
