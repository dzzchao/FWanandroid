package com.dzzchao.fwanandroid

import android.app.Application
import android.content.Context
import timber.log.Timber
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

const val BASEURL = "https://www.wanandroid.com"

class App : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            val clazz = ActivityLifecycleCallbacks::class.java
            val callback = Proxy.newProxyInstance(
                clazz.classLoader,
                arrayOf(clazz)
            ) { _, method, args ->
                Timber.d(
                    "LifeCycle: %s  %s", args!![0].javaClass.simpleName,
                    method?.name
                )
                Unit
            } as ActivityLifecycleCallbacks
            registerActivityLifecycleCallbacks(callback)
        }
    }
}