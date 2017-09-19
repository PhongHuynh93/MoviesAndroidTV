package com.example.myapplication.data.remote;

import com.example.myapplication.data.local.TheMovieDbAPI;
import com.example.myapplication.reposition.MovieDataSource;

/**
 * Created by CPU11112-local on 9/12/2017.
 */
public class MovieRemoteDataSource implements MovieDataSource {
    private final TheMovieDbAPI mMovieRetrofitEndpoint;

    public MovieRemoteDataSource(TheMovieDbAPI movieRetrofitEndpoint) {
        this.mMovieRetrofitEndpoint = movieRetrofitEndpoint;
    }

//    @Override
//    public Observable<DiscoverMovieResponse> getRemoteDatas(String sortBy, Integer page) {
//        return mMovieRetrofitEndpoint.discoverMovies(sortBy, page);
//    }
}
