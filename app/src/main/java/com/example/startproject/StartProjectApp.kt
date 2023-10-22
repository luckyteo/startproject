package com.example.startproject

import android.app.Application
import android.content.Context

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
}