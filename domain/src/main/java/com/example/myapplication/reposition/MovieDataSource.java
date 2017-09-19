package com.example.myapplication.reposition;


import com.example.myapplication.MovieResponse;
import com.example.myapplication.data.models.CreditsResponse;
import com.example.myapplication.data.models.MovieDetails;

import io.reactivex.Observable;

/**
 * Created by CPU11112-local on 9/12/2017.
 */

public interface MovieDataSource {
    Observable<MovieResponse> getListMovies(int tag, String page);

    Observable<CreditsResponse> fetchCastMembers(String id);

    Observable<MovieResponse> fetchRecommendations(String id);

    Observable<MovieDetails> fetchMovieDetails(String id);
}
