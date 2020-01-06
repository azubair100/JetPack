package com.zubair.kotlinjetpack.di.modules

import android.app.Application
import com.zubair.kotlinjetpack.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module //Modules have functionalities that we want to inject
class PrefsModule {

    //If you need application context passed, create a new module for dagger to handle that
    //Check AppModule

    /*@Provides allows us to inject Application where ever we want to
       Basically we will provide a Application somewhere

     @Singleton means a single instance of a class
      We want our SharedPreferencesHelper as a singleton because it accesses one resource in the
      Android system which is SharedPreference storage, So no multiple at the same time

      This specific @Provides an object SharedPreferencesHelper() takes in an Application object
      We want Dagger to handle it automatically, so create AppModule
    */
    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferencesHelper
            = SharedPreferencesHelper(app)
}