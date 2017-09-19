package com.example.myapplication.reposition;


import com.example.myapplication.MovieResponse;

import io.reactivex.Observable;

/**
 * Created by CPU11112-local on 9/12/2017.
 */

public interface MovieDataSource {
    Observable<MovieResponse> getListMovies(int tag, String page);
}
