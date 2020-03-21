package com.dzzchao.fwanandroid.storage.sp

import android.content.Context
import com.dzzchao.fwanandroid.App


class SPHelper {

    companion object {
        private val sharedPreferences =
            App.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
        private var editor = sharedPreferences.edit()

        fun putString(key: String, value: String) =
            editor.putString(key, value).apply()


        fun putBoolean(key: String, value: Boolean) =
            editor.putBoolean(key, value).apply()

        fun getBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)



    }


}

