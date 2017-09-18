package com.gabilheri.moviestmdb.util;

import android.support.annotation.IntDef;

/**
 * Created by user on 9/17/2017.
 */

public class Constant {
    public static final int BACKGROUND_UPDATE_DELAY = 300;
    public static final int GRID_ITEM_HEIGHT = 200;

    @IntDef({NOW_PLAYING, TOP_RATED, POPULAR, UPCOMING, SETTING})
    public @interface TypeMovieMode {}
    public static final int NOW_PLAYING = 0;
    public static final int TOP_RATED = 1;
    public static final int POPULAR = 2;
    public static final int UPCOMING = 3;
    public static final int SETTING = 4;
}
