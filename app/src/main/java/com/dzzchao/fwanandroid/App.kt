package com.dzzchao.fwanandroid

import android.app.Application
import android.content.Context
import timber.log.Timber

const val BASEURL = "https://www.wanandroid.com"

class App : Application() {

    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}