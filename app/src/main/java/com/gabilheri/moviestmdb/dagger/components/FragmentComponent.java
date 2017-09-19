package com.gabilheri.moviestmdb.dagger.components;


import com.gabilheri.moviestmdb.dagger.PerFragment;
import com.gabilheri.moviestmdb.dagger.modules.FragmentModule;
import com.gabilheri.moviestmdb.ui.main.MainFragment;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerFragment
//@Component(dependencies = MovieComponent.class, modules = FragmentModule.class)
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(MainFragment fragment);
}
