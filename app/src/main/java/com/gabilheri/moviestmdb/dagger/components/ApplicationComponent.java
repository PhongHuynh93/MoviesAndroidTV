package com.gabilheri.moviestmdb.dagger.components;


import com.example.myapplication.module.HttpClientModule;
import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.dagger.AppScope;
import com.gabilheri.moviestmdb.dagger.modules.ApplicationModule;
import com.gabilheri.moviestmdb.dagger.modules.FragmentModule;
import com.gabilheri.moviestmdb.dagger.modules.RepositionModule;
import com.gabilheri.moviestmdb.data.contentprovider.SampleContentProvider;
import com.gabilheri.moviestmdb.data.recommendation.UpdateRecommendationService;
import com.gabilheri.moviestmdb.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/4/16.
 */
@AppScope
@Singleton
@Component(modules = {
        ApplicationModule.class,
        HttpClientModule.class,
        RepositionModule.class
})
public interface ApplicationComponent {
    FragmentComponent newSubFragmentComponent(FragmentModule fragmentModule);

    void inject(App app);

    //    void inject(MainFragment mainFragment);
//    void inject(MovieDetailsFragment fragment);
    void inject(MainActivity fragment);

    void inject(UpdateRecommendationService fragment);

    void inject(SampleContentProvider fragment);
}
