package com.example.myapplication.interactor;

import com.example.myapplication.data.models.MovieDetails;
import com.example.myapplication.reposition.MovieDataSource;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by CPU11112-local on 9/11/2017.
 */
public class GetMovieDetail extends UseCase<GetMovieDetail.RequestValues, GetMovieDetail.ResponseValue>{
    private final MovieDataSource movieReposition;

    public GetMovieDetail(MovieDataSource movieReposition) {
        super();
        this.movieReposition = movieReposition;
    }


    @Override
    protected Observable<ResponseValue> buildUseCaseObservable(RequestValues requestValues) {
        return movieReposition.fetchMovieDetails(requestValues.getId())
                .map(new Function<MovieDetails, ResponseValue>() {
                    @Override
                    public ResponseValue apply(MovieDetails movieResponse) throws Exception {
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
        private final MovieDetails movieResponse;

        public ResponseValue(MovieDetails movieResponse) {
            this.movieResponse = movieResponse;
        }

        public MovieDetails getMovieResponse() {
            return movieResponse;
        }
    }
}
