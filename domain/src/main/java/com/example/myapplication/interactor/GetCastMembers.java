package com.example.myapplication.interactor;

import com.example.myapplication.data.models.CreditsResponse;
import com.example.myapplication.reposition.MovieDataSource;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by CPU11112-local on 9/11/2017.
 */
public class GetCastMembers extends UseCase<GetCastMembers.RequestValues, GetCastMembers.ResponseValue>{
    private final MovieDataSource movieReposition;

    public GetCastMembers(MovieDataSource movieReposition) {
        super();
        this.movieReposition = movieReposition;
    }


    @Override
    protected Observable<ResponseValue> buildUseCaseObservable(RequestValues requestValues) {
        return movieReposition.fetchCastMembers(requestValues.getId())
                .map(new Function<CreditsResponse, ResponseValue>() {
                    @Override
                    public ResponseValue apply(CreditsResponse movieResponse) throws Exception {
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
        private final CreditsResponse movieResponse;

        public ResponseValue(CreditsResponse movieResponse) {
            this.movieResponse = movieResponse;
        }

        public CreditsResponse getMovieResponse() {
            return movieResponse;
        }
    }
}
