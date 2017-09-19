package com.gabilheri.moviestmdb.presenter;

import android.support.annotation.NonNull;

import com.example.myapplication.interactor.GetCastMembers;
import com.example.myapplication.interactor.GetMovieDetail;
import com.example.myapplication.interactor.GetRecommendations;
import com.gabilheri.moviestmdb.ui.detail.MovieDetailView;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by CPU11112-local on 9/11/2017.
 */

public class DetailMoviePresenter extends BasePresenter<MovieDetailView> {
    private final GetCastMembers getCastMembers;
    private final GetMovieDetail getMovieDetail;
    private final GetRecommendations getRecommendations;

    @Inject
    public DetailMoviePresenter(@NonNull GetCastMembers getCastMembers,
                                @NonNull GetMovieDetail getMovieDetail,
                                @NonNull GetRecommendations getRecommendations) {
        this.getCastMembers = getCastMembers;
        this.getMovieDetail = getMovieDetail;
        this.getRecommendations = getRecommendations;
    }

    public void fetchCastMembers(String id) {
        getCastMembers.execute(new CastMemberObserver(), new GetCastMembers.RequestValues(id));
    }

    public void fetchRecommendations(String id) {
        getRecommendations.execute(new RecommendationsObserver(), new GetRecommendations.RequestValues(id));
    }

    public void fetchMovieDetails(String id) {
        getMovieDetail.execute(new MovieDetailObserver(), new GetMovieDetail.RequestValues(id));
    }

    private final class CastMemberObserver extends DisposableObserver<GetCastMembers.ResponseValue> {
        @Override
        public void onNext(@io.reactivex.annotations.NonNull GetCastMembers.ResponseValue responseValue) {
            getMvpView().bindCastMembers(responseValue.getMovieResponse());
        }

        @Override
        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }

    private final class MovieDetailObserver extends DisposableObserver<GetMovieDetail.ResponseValue> {

        @Override
        public void onNext(@io.reactivex.annotations.NonNull GetMovieDetail.ResponseValue responseValue) {
            getMvpView().bindMovieDetails(responseValue.getMovieResponse());
        }

        @Override
        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }

    private final class RecommendationsObserver extends DisposableObserver<GetRecommendations.ResponseValue> {

        @Override
        public void onNext(@io.reactivex.annotations.NonNull GetRecommendations.ResponseValue responseValue) {
            getMvpView().bindRecommendations(responseValue.getMovieResponse());
        }

        @Override
        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }
}
