package com.zubair.kotlinjetpack.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {
    companion object {
        //null by default
        private var prefs: SharedPreferences? = null
        private const val PREF_TIME = "Pref Time"

        //using shared preference helper only once
        @Volatile
        private var instance: SharedPreferencesHelper? = null
        private var LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper =
            instance ?: synchronized(LOCK) {
                instance ?: buildHelper(context).also { instance = it }
            }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    //setter
    fun saveUpdateTime(time: Long) { prefs?.edit(commit = true) { putLong(PREF_TIME, time) } }

    //getter
    fun getUpdateTime() = prefs?.getLong(PREF_TIME, 0)

    //exact key as described in the preferences.xml, this is a getter, preference.xml
    //is a setter
    fun getCachedDuration() = prefs?.getString("pref_cache_duration", "")

}