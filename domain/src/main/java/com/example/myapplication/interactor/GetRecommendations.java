package com.example.myapplication.interactor;

import com.example.myapplication.MovieResponse;
import com.example.myapplication.reposition.MovieDataSource;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by CPU11112-local on 9/11/2017.
 */
public class GetRecommendations extends UseCase<GetRecommendations.RequestValues, GetRecommendations.ResponseValue>{
    private final MovieDataSource movieReposition;

    public GetRecommendations(MovieDataSource movieReposition) {
        super();
        this.movieReposition = movieReposition;
    }


    @Override
    protected Observable<ResponseValue> buildUseCaseObservable(RequestValues requestValues) {
        return movieReposition.fetchRecommendations(requestValues.getId())
                .map(new Function<MovieResponse, ResponseValue>() {
                    @Override
                    public ResponseValue apply(MovieResponse movieResponse) throws Exception {
                        return new ResponseValue(movieResponse);
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String mId;

        public RequestValues(String id) {
            this.mId = id;
        }

        public String getId() {
            return mId;
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
