package com.example.statustracker

import android.app.Application
import com.example.statustracker.di.module.Test
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class StatusTracker:Application() {

    override fun onCreate() {
        super.onCreate()
        config()
    }
    private fun config() {
        startKoin{
            androidContext(this@StatusTracker)
            androidLogger(Level.DEBUG)
            modules(Test.modules())
        }
    }
}