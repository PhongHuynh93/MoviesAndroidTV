package com.example.myapplication.interactor;

import com.example.myapplication.MovieResponse;
import com.example.myapplication.reposition.MovieDataSource;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by CPU11112-local on 9/11/2017.
 */
public class SearchMovieUsecase extends UseCase<SearchMovieUsecase.RequestValues, SearchMovieUsecase.ResponseValue>{
    private final MovieDataSource movieReposition;

    public SearchMovieUsecase(MovieDataSource movieReposition) {
        super();
        this.movieReposition = movieReposition;
    }

    @Override
    protected Observable<ResponseValue> buildUseCaseObservable(RequestValues requestValues) {
        return movieReposition.searchMovie(requestValues.getQuery())
                .map(new Function<MovieResponse, ResponseValue>() {
                    @Override
                    public ResponseValue apply(MovieResponse movieResponse) throws Exception {
                        return new ResponseValue(movieResponse);
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String mQuery;

        public RequestValues(String query) {
            this.mQuery = query;
        }

        public String getQuery() {
            return mQuery;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final MovieResponse movieResponse;

        public ResponseValue(MovieResponse movieResponse) {
            this.movieResponse = movieResponse;
        }

        public MovieResponse getMovieResponse() {
            return movieResponse;
        }
    }
}
