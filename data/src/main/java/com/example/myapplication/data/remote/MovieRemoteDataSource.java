package com.example.myapplication.data.remote;

import com.example.myapplication.MovieResponse;
import com.example.myapplication.data.local.TheMovieDbAPI;
import com.example.myapplication.data.models.CreditsResponse;
import com.example.myapplication.data.models.MovieDetails;
import com.example.myapplication.reposition.MovieDataSource;
import com.example.myapplication.util.Constant;

import io.reactivex.Observable;

/**
 * Created by CPU11112-local on 9/12/2017.
 */
public class MovieRemoteDataSource implements MovieDataSource {
    private final TheMovieDbAPI mDbAPI;

    public MovieRemoteDataSource(TheMovieDbAPI movieRetrofitEndpoint) {
        this.mDbAPI = movieRetrofitEndpoint;
    }

    @Override
    public Observable<MovieResponse> getListMovies(int tag, String page) {

        Observable<MovieResponse> movieResponseObservable;
        switch (tag) {
            case Constant.NOW_PLAYING:
                movieResponseObservable = mDbAPI.getNowPlayingMovies(Constant.API_KEY_URL, page);
                break;
            case Constant.POPULAR:
                movieResponseObservable = mDbAPI.getPopularMovies(Constant.API_KEY_URL, page);
                break;
            case Constant.TOP_RATED:
                movieResponseObservable = mDbAPI.getTopRatedMovies(Constant.API_KEY_URL, page);
                break;
            case Constant.UPCOMING:
                movieResponseObservable = mDbAPI.getUpcomingMovies(Constant.API_KEY_URL, page);
                break;
            case Constant.SETTING:
            default:
                throw new IllegalArgumentException("Not have this argument");
        }

        return movieResponseObservable;
    }

    @Override
    public Observable<CreditsResponse> fetchCastMembers(String id) {
        return mDbAPI.getCredits(id, Constant.API_KEY_URL);
    }

    @Override
    public Observable<MovieResponse> fetchRecommendations(String id) {
        return mDbAPI.getRecommendations(id, Constant.API_KEY_URL);
    }

    @Override
    public Observable<MovieDetails> fetchMovieDetails(String id) {
        return mDbAPI.getMovieDetails(id, Constant.API_KEY_URL);
    }

    @Override
    public Observable<MovieResponse> searchMovie(String query) {
        return mDbAPI.searchMovies(query, Constant.API_KEY_URL);
    }
}
