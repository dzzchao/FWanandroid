package com.dzzchao.fwanandroid

import android.app.Application
import timber.log.Timber

const val BASEURL = "https://www.wanandroid.com"

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}