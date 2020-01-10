package com.iww.classifiedolx.api;

import android.app.Application;
import android.content.Context;
import com.iww.classifiedolx.api.interfaces.ApplicationContext;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    public Context provideContext() {
        return this.application;
    }



}
