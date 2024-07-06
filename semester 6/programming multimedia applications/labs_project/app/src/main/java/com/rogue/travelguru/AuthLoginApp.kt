package com.rogue.travelguru

import android.app.Application
import com.google.firebase.FirebaseApp
import com.rogue.travelguru.data.AppContainer
import com.rogue.travelguru.data.DefaultAppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AuthLoginApp: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}