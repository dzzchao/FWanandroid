package com.dzzchao.fwanandroid.utils

import android.widget.Toast
import com.dzzchao.fwanandroid.App
import timber.log.Timber

fun showToast(msg: String) {
    Timber.d("showToast$msg")
    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show()
}

fun showToast(msgId: Int) {
    Timber.d("showToast${App.context.getString(msgId)}")
    Toast.makeText(App.context, App.context.getString(msgId), Toast.LENGTH_SHORT).show()
}
