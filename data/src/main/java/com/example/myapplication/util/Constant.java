package com.example.myapplication.util;

import android.provider.BaseColumns;
import android.support.annotation.IntDef;

/**
 * Created by CPU11112-local on 9/19/2017.
 */

public class Constant {
    public static final int NOW_PLAYING = 0;
    public static final int TOP_RATED = 1;
    public static final int POPULAR = 2;
    public static final int UPCOMING = 3;
    public static final int SETTING = 4;
    public static final int MORE_SAMPLE = 5;
    public static final int OPTION = 6;
    public static final String API_KEY_URL = "53470b56a60668274e1dd9f84d882564";
    /**
     * The name of the Cheese table.
     */
    public static final String TABLE_NAME = "cheeses";
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    /**
     * The name of the name column.
     */
    public static final String COLUMN_NAME = "name";

    @IntDef({NOW_PLAYING, TOP_RATED, POPULAR, UPCOMING, SETTING})
    public @interface TypeMovieMode {
    }
    /**
     * Dummy data.
     */
}
