package com.example.myapplication.interactor;

import com.example.myapplication.MovieResponse;
import com.example.myapplication.reposition.MovieDataSource;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by CPU11112-local on 9/11/2017.
 */
public class GetMovieList extends UseCase<GetMovieList.RequestValues, GetMovieList.ResponseValue>{
    private final MovieDataSource movieReposition;

    public GetMovieList(MovieDataSource movieReposition) {
        super();
        this.movieReposition = movieReposition;
    }


    @Override
    protected Observable<ResponseValue> buildUseCaseObservable(RequestValues requestValues) {
        return movieReposition.getListMovies(requestValues.getTag(), requestValues.getPage())
                .map(new Function<MovieResponse, ResponseValue>() {
                    @Override
                    public ResponseValue apply(MovieResponse movieResponse) throws Exception {
                        return new ResponseValue(movieResponse);
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String page;
        private final int tag;

        public RequestValues(int tag, String page) {
            this.tag = tag;
            this.page = page;
        }

        public String getPage() {
            return page;
        }

        public int getTag() {
            return tag;
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
