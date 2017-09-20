package com.gabilheri.moviestmdb.ui.search;

import com.example.myapplication.MovieResponse;
import com.gabilheri.moviestmdb.ui.MvpView;

/**
 * Created by CPU11112-local on 9/11/2017.
 */

public interface SearchView extends MvpView {
    void showData(MovieResponse movieResponse);
}
