package com.gabilheri.moviestmdb.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.myapplication.data.MovieReposition;
import com.example.myapplication.data.local.TheMovieDbAPI;
import com.example.myapplication.data.local.contentProvider.CheeseDao;
import com.example.myapplication.data.local.contentProvider.SampleDatabase;
import com.example.myapplication.data.remote.MovieRemoteDataSource;
import com.example.myapplication.interactor.GetCastMembers;
import com.example.myapplication.interactor.GetMovieDetail;
import com.example.myapplication.interactor.GetMovieList;
import com.example.myapplication.interactor.GetRecommendations;
import com.example.myapplication.interactor.SearchMovieUsecase;
import com.example.myapplication.reposition.MovieDataSource;
import com.gabilheri.moviestmdb.util.AccountUtils;
import com.gabilheri.moviestmdb.util.Constant;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CPU11112-local on 9/11/2017.
 */
@Module
public class RepositionModule {
    @Provides
    @Singleton
    SharedPreferences getSharePreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    AccountUtils getAccountHelper(SharedPreferences sharedPreferences) {
        return new AccountUtils(sharedPreferences);
    }

    @Provides
    @Singleton
    @Named(Constant.MOVIE_REPOSITION)
    MovieDataSource getMovieReposition(@Named(Constant.MOVIE_REMOTE) MovieDataSource remotemovie) {
        return new MovieReposition(remotemovie);
    }

    @Provides
    @Singleton
    GetMovieList usecaseGetMovie(@Named(Constant.MOVIE_REPOSITION) MovieDataSource movieDataSource) {
        return new GetMovieList(movieDataSource);
    }

    @Provides
    @Singleton
    GetCastMembers usecaseGetCastMembers(@Named(Constant.MOVIE_REPOSITION) MovieDataSource movieDataSource) {
        return new GetCastMembers(movieDataSource);
    }

    @Provides
    @Singleton
    GetMovieDetail usecaseGetMovieDetail(@Named(Constant.MOVIE_REPOSITION) MovieDataSource movieDataSource) {
        return new GetMovieDetail(movieDataSource);
    }

    @Provides
    @Singleton
    GetRecommendations usecaseGetRecommendations(@Named(Constant.MOVIE_REPOSITION) MovieDataSource movieDataSource) {
        return new GetRecommendations(movieDataSource);
    }

    @Provides
    @Singleton
    SearchMovieUsecase usecaseGetSearch(@Named(Constant.MOVIE_REPOSITION) MovieDataSource movieDataSource) {
        return new SearchMovieUsecase(movieDataSource);
    }
//
//    @Provides
//    @Singleton
//    @Named(Constant.MOVIE_LOCAL)
//    MovieDataSource getLocalMovie(Context context) {
//        return new MovieLocalDataSource(context);
//    }

    @Provides
    @Singleton
    @Named(Constant.MOVIE_REMOTE)
    MovieDataSource getRemoteMovie(TheMovieDbAPI movieRetrofitEndpoint) {
        return new MovieRemoteDataSource(movieRetrofitEndpoint);
    }

    // step - content provider
    @Provides
    @Singleton
    SampleDatabase getSampleDatabase(Context context) {
        return SampleDatabase.getSampleDatabase(context);
    }

    @Provides
    @Singleton
    CheeseDao getSampleDatabase(SampleDatabase sampleDatabase) {
        return sampleDatabase.cheese();
    }
//    @Provides
//    @Singleton
//    AppLocalDatabase getAppLocalDatabase(Context context) {
//        return AppLocalDatabase.getAppDatabase(context);
//    }
}
