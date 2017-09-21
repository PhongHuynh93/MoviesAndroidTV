package com.gabilheri.moviestmdb.util;

import com.example.myapplication.data.models.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/17/2017.
 */

public class Constant {
    public static final int BACKGROUND_UPDATE_DELAY = 300;
    public static final int GRID_ITEM_HEIGHT = 200;

    // action in detailsfragment
    public static final long ACTION_WATCH_TRAILER = 1;

    // movie reposition
    public static final String MOVIE_REPOSITION = "MOVIE_REPOSITION";
    public static final String MOVIE_LOCAL = "MOVIE_LOCAL";
    public static final String MOVIE_REMOTE = "MOVIE_REMOTE";


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
