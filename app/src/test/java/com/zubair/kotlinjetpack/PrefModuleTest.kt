package com.zubair.kotlinjetpack

import android.app.Application
import com.zubair.kotlinjetpack.di.modules.PrefsModule
import com.zubair.kotlinjetpack.util.SharedPreferencesHelper

class PrefModuleTest(val mockPrefs: SharedPreferencesHelper): PrefsModule() {
    override fun provideSharedPreferencesApplicationLevel(app: Application): SharedPreferencesHelper =
        mockPrefs
}