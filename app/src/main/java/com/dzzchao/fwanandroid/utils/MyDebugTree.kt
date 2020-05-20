package com.dzzchao.fwanandroid.utils

import android.util.Log
import timber.log.Timber

class MyDebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)
    }
}