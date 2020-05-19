package com.dzzchao.fwanandroid

import android.app.Application
import android.content.Context
import android.os.Debug
import android.os.Environment
import androidx.core.os.TraceCompat
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
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
        //traceview
        //Debug.startMethodTracing("app")
        //TraceCompat.beginSection("apponCreate")

        context = applicationContext

        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            registerLifecycle()
        }

        initXlog()


        //Debug.stopMethodTracing()
        //TraceCompat.endSection()
    }

    private fun initXlog() {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");

        val SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
        val logPath = SDCARD + "/marssample/log";

        // this is necessary, or may cash for SIGBUS
        val cachePath = this.getFilesDir().path + "/xlog"

        //init xlog
        if (BuildConfig.DEBUG) {
            Xlog.appenderOpen(
                Xlog.LEVEL_DEBUG,
                Xlog.AppednerModeAsync,
                cachePath,
                logPath,
                "MarsSample",
                0,
                ""
            )
            Xlog.setConsoleLogOpen(true);

        } else {
            Xlog.appenderOpen(
                Xlog.LEVEL_INFO,
                Xlog.AppednerModeAsync,
                cachePath,
                logPath,
                "MarsSample",
                0,
                ""
            )
            Xlog.setConsoleLogOpen(false);
        }

        Log.setLogImp(Xlog())
    }

    /**
     * 监听生命周期
     */
    private fun registerLifecycle() {
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


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //可以再这里记录开始时间，然后到自己的feed流的第一条数据显示的时间，可以当做是App的启动时间来计算
        //可以线上采集

    }
}