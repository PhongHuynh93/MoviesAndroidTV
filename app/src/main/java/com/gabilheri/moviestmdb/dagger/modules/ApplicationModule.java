package com.gabilheri.moviestmdb.dagger.modules;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by <a href="mailto:marcus@gabilheri.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/4/16.
 */
@Module
public class ApplicationModule {

    Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    Application providesApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    Context providesApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @Singleton
    @Provides
    NotificationManager providesNotificationManager(Context context) {
        return (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
