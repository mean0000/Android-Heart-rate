package com.example.test_00

import android.app.Application
import com.deepmedi.dm_data.DeepMediAlgorithm

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DeepMediAlgorithm.init()
    }
}

annotation class HiltAndroidApp
