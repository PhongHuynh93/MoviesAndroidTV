package com.example.myapplication.interactor;

import com.example.myapplication.reposition.MovieDataSource;

import io.reactivex.Observable;

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
//        return movieReposition.getRemoteDatas(requestValues.getSortBy(), requestValues.getPage())
//                .map(ResponseValue::new);
        return null;
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String sortBy;
        private final int page;

        public RequestValues(String sortBy, int page) {
            this.sortBy = sortBy;
            this.page = page;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
//        private final DiscoverMovieResponse discoverMovieResponse;
//
//        public ResponseValue(@NonNull DiscoverMovieResponse discoverMovieResponse) {
//            this.discoverMovieResponse = discoverMovieResponse;
//        }
    }
}
