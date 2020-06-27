package com.dzzchao.fwanandroid.utils

import com.tencent.mars.xlog.Log
import timber.log.Timber

class MyDebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val newMessage = "fwandroid=>$message"
        super.log(priority, tag, newMessage, t)
    }

}