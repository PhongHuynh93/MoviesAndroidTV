package com.example.myapplication.data;

import com.example.myapplication.MovieResponse;
import com.example.myapplication.data.models.CreditsResponse;
import com.example.myapplication.data.models.MovieDetails;
import com.example.myapplication.reposition.MovieDataSource;

import io.reactivex.Observable;

/**
 * Created by CPU11112-local on 9/12/2017.
 */
public class MovieReposition implements MovieDataSource {
    private final MovieDataSource mRemoteMovieDataSource;

    public MovieReposition(MovieDataSource remotemovie) {
        mRemoteMovieDataSource = remotemovie;
    }

    @Override
    public Observable<MovieResponse> getListMovies(int tag, String page) {
        return mRemoteMovieDataSource.getListMovies(tag, page);
    }

    @Override
    public Observable<CreditsResponse> fetchCastMembers(String id) {
        return mRemoteMovieDataSource.fetchCastMembers(id);
    }

    @Override
    public Observable<MovieResponse> fetchRecommendations(String id) {
        return mRemoteMovieDataSource.fetchRecommendations(id);
    }

    @Override
    public Observable<MovieDetails> fetchMovieDetails(String id) {
        return mRemoteMovieDataSource.fetchMovieDetails(id);
    }

    // TODO: 9/12/2017 apply rx here to get from local data when there is no network
//    @Override
//    public Observable<DiscoverMovieResponse> getRemoteDatas(String sortBy, Integer page) {
//        // test: 9/13/2017 save the data into local
////        Maybe<DiscoverMovieResponse> localSource =
////                mAppLocalDatabase.userDao().loadAllDiscoverMovieByType(sortBy)
////                .map(new Function<List<DiscoverMovie>, DiscoverMovieResponse>() {
////                    @Override
////                    public DiscoverMovieResponse apply(@NonNull List<DiscoverMovie> discoverMovies) throws Exception {
////                        return new DiscoverMovieResponse(discoverMovies);
////                    }
////                });
//        Observable<DiscoverMovieResponse> remoteSource = mRemoteMovieDataSource.getRemoteDatas(sortBy, page)
//                .doOnNext(new Consumer<DiscoverMovieResponse>() {
//                    @Override
//                    public void accept(DiscoverMovieResponse discoverMovieResponse) throws Exception {
//                        // save list of data into rooms
//                        for (DiscoverMovie discoverMovie : discoverMovieResponse.getResults()) {
//                            discoverMovie.setSortBy(discoverMovieResponse.getSortBy());
//                        }
//                        mAppLocalDatabase.userDao().addDiscoverMovie(discoverMovieResponse.getResults());
//                    }
//                });
////        Maybe<DiscoverMovieResponse> source =
////                Maybe.concat(localSource, remoteSource.singleElement()).firstElement();
//        return remoteSource;
//    }
}
