package com.zubair.kotlinjetpack.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module //Modules have functionalities that we want to inject
class AppModule(val app: Application) {
    @Provides /*Allows us to inject Application where ever we want to
    Basically we will provide a Application somewhere*/
    fun provideApplicationContext(): Application = app
}