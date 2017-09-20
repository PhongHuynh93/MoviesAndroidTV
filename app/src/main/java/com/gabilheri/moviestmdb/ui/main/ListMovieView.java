package com.gabilheri.moviestmdb.ui.main;

import com.example.myapplication.MovieResponse;
import com.gabilheri.moviestmdb.ui.MvpView;

/**
 * Created by CPU11112-local on 9/11/2017.
 */

public interface ListMovieView extends MvpView {
    void showData(int id, MovieResponse movieResponse);

    void showLoadingIndicator(int tag);

    void hideLoadingIndicator(int tag);

    void showTryAgainLayout(int tag);
}
