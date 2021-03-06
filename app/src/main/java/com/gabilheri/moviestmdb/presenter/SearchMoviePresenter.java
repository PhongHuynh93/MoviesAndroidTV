package com.gabilheri.moviestmdb.presenter;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.example.myapplication.interactor.SearchMovieUsecase;
import com.gabilheri.moviestmdb.ui.search.SearchView;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by CPU11112-local on 9/11/2017.
 */

public class SearchMoviePresenter extends BasePresenter<SearchView> {
    private final SearchMovieUsecase mSearchMovieUsecase;

    @Inject
    public SearchMoviePresenter(
            @NonNull Fragment fragment, @NonNull SearchMovieUsecase searchMovieUsecase) {
        super(fragment);
        this.mSearchMovieUsecase = searchMovieUsecase;
    }

    public void clearSubscription() {
        mSearchMovieUsecase.dispose();
    }

    public void searchMovie(String query) {
        clearSubscription();
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
