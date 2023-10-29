package com.example.startproject

import android.app.Application
import android.content.Context
import com.example.startproject.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class StartProjectApp : Application() {

    companion object {
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun application(): Application? {
            return instance
        }

        private var instance: StartProjectApp? = null

        var debug: Boolean = true

        fun clear() {

        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@StartProjectApp)
            modules(appModules)
        }
    }
}