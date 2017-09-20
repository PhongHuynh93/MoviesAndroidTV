package com.gabilheri.moviestmdb;

import android.support.multidex.MultiDexApplication;

import com.example.myapplication.module.HttpClientModule;
import com.gabilheri.moviestmdb.dagger.components.ApplicationComponent;
import com.gabilheri.moviestmdb.dagger.components.DaggerApplicationComponent;
import com.gabilheri.moviestmdb.dagger.modules.ApplicationModule;
import com.gabilheri.moviestmdb.dagger.modules.RepositionModule;

import timber.log.Timber;

/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 10/11/16.
 */

public class App extends MultiDexApplication {

    private static App instance;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // Creates Dagger Graph
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpClientModule(new HttpClientModule())
                .repositionModule(new RepositionModule())
                .build();

        mApplicationComponent.inject(this);
    }

    public static App instance() {
        return instance;
    }

    public ApplicationComponent appComponent() {
        return mApplicationComponent;
    }

}
