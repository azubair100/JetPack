package com.zubair.kotlinjetpack.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: Application) {
    @Provides
    fun provideApplicationContext(): Application = app
}