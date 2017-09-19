package com.gabilheri.moviestmdb.ui.detail;

import com.example.myapplication.MovieResponse;
import com.example.myapplication.data.models.CreditsResponse;
import com.example.myapplication.data.models.MovieDetails;
import com.gabilheri.moviestmdb.ui.MvpView;

/**
 * Created by CPU11112-local on 9/11/2017.
 */

public interface MovieDetailView extends MvpView {
    void bindCastMembers(CreditsResponse movieResponse);

    void bindMovieDetails(MovieDetails movieResponse);

    void bindRecommendations(MovieResponse movieResponse);
}
