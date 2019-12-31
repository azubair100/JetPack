package com.zubair.kotlinjetpack.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

import com.zubair.kotlinjetpack.R

//make sure to create preferences.xml first, then navigation.xml etc
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
