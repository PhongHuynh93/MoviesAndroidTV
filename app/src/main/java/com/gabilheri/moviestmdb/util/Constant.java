package com.gabilheri.moviestmdb.util;

import com.example.myapplication.data.models.Tag;
import com.gabilheri.moviestmdb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/17/2017.
 */

public class Constant {
    public static final int ERROR_CODE = -1;

    public static final int BACKGROUND_UPDATE_DELAY = 300;
    public static final int GRID_ITEM_HEIGHT = 200;

    // action in detailsfragment
    public static final int ACTION_WATCH_TRAILER = 1;
    public static final int ACTION_BUY = 2;

    // movie reposition
    public static final String MOVIE_REPOSITION = "MOVIE_REPOSITION";
    public static final String MOVIE_LOCAL = "MOVIE_LOCAL";
    public static final String MOVIE_REMOTE = "MOVIE_REMOTE";
//    info guide fragment
    public static final int CONTINUE = 0;
    public static final int BACK = 1;
    public static final int SHOW_MORE = 3;
    public static final int OPTION_CHECK_SET_ID = 10;
    public static final String[] OPTION_NAMES = {
            "Option A",
            "Option B",
            "Option C"
    };
    public static final String[] OPTION_DESCRIPTIONS = {
            "Here's one thing you can do",
            "Here's another thing you can do",
            "Here's one more thing you can do"
    };
    public static final int[] OPTION_DRAWABLES = {R.drawable.ic_guidedstep_option_a,
            R.drawable.ic_guidedstep_option_b, R.drawable.ic_guidedstep_option_c};
    public static final boolean[] OPTION_CHECKED = {true, false, false};
    // info - list of movies is on the right of the header
    public static List<Tag> list;

    public static List<Tag> setupTags() {
        list = new ArrayList<Tag>();

        list.add(new Tag(0, "It", 10));
        list.add(new Tag(1, "The Dark Tower", 10));
        list.add(new Tag(2, "Annabelle", 10));
        return list;
    }

}
