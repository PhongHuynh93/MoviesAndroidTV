package com.gabilheri.moviestmdb.dagger.components;


import com.gabilheri.moviestmdb.dagger.PerFragment;
import com.gabilheri.moviestmdb.dagger.modules.FragmentModule;
import com.gabilheri.moviestmdb.ui.detail.MovieDetailsFragment;
import com.gabilheri.moviestmdb.ui.main.MainFragment;
import com.gabilheri.moviestmdb.ui.onboard.OnboardingFragment;
import com.gabilheri.moviestmdb.ui.search.SearchFragment;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerFragment
//@Component(dependencies = MovieComponent.class, modules = FragmentModule.class)
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(MainFragment fragment);
    void inject(MovieDetailsFragment fragment);
    void inject(SearchFragment fragment);
    void inject(OnboardingFragment fragment);
}
