package com.zubair.kotlinjetpack.di.modules

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.zubair.kotlinjetpack.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
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
    @TypeOfContext(CONTEXT_APP)
    fun provideSharedPreferencesApplicationLevel(app: Application): SharedPreferencesHelper
            = SharedPreferencesHelper(app)

    /*Todo: Dagger Goal #4: What if we need to provideSharedPref() method where
       it takes an Activity level Context param
    Dagger's solution @Qualifer
     Step 1: Create the provideSharedPreferencesActivityLevel()
     Step 2: Create the @Qualifier TypeOfContext Class below that()
     Step 3: Create CONTEXT_APP & CONTEXT_ACTIVITY
     Step 4: Add @TypeOfContext() to the respective methods
     */

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideSharedPreferencesActivityLevel(activity: AppCompatActivity): SharedPreferencesHelper
            = SharedPreferencesHelper(activity)

}

const val CONTEXT_APP = "Application Context"
const val CONTEXT_ACTIVITY = "Application Activity"

@Qualifier
annotation class TypeOfContext(val type: String)