package com.zubair.kotlinjetpack.di.modules

import android.app.Application
import com.zubair.kotlinjetpack.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    //If you need application context passed, create a new module for dagger to handle that
    //Check AppModule
    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferencesHelper
            = SharedPreferencesHelper(app)
}