package com.gabilheri.moviestmdb.presenter;

import android.support.annotation.NonNull;

import com.example.myapplication.interactor.GetMovieList;
import com.example.myapplication.interactor.SearchMovieUsecase;
import com.gabilheri.moviestmdb.ui.main.ListMovieView;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by CPU11112-local on 9/11/2017.
 */

public class SearchMoviePresenter extends BasePresenter<ListMovieView> {
    private final SearchMovieUsecase mSearchMovieUsecase;

    @Inject
    public SearchMoviePresenter(@NonNull SearchMovieUsecase searchMovieUsecase) {
//                              @NonNull ClearCompleteTasks clearCompleteTasks) {
        this.mSearchMovieUsecase = searchMovieUsecase;
    }


    public void searchMovie(String query) {
        mSearchMovieUsecase.execute(new MovieListObserver(), new SearchMovieUsecase.RequestValues(query));
    }

    private final class MovieListObserver extends DisposableObserver<SearchMovieUsecase.ResponseValue> {

        @Override
        public void onNext(@io.reactivex.annotations.NonNull SearchMovieUsecase.ResponseValue responseValue) {
//            getMvpView().hideLoadingIndicator(id);
            getMvpView().showData(responseValue.getMovieResponse());
        }

        @Override
        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//            getMvpView().hideLoadingIndicator(id);
//            getMvpView().showTryAgainLayout(id);
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }
}
