package com.daou.timesapp

import android.app.Application
import com.daou.timesapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TimesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TimesApp)
            androidLogger()
            modules(appModules)
        }
    }
}