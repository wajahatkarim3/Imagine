package com.wajahatkarim3.imagine

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.wajahatkarim3.imagine.utils.isNight
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImagineApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupDayNightMode()
    }

    fun setupDayNightMode() {
        // Get UI mode and set
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}