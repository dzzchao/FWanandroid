package com.dzzchao.fwanandroid.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.storage.sp.SPHelper.Companion.getBoolean
import com.dzzchao.fwanandroid.storage.sp.spIsLogin
import com.dzzchao.fwanandroid.ui.login.LoginActivity
import timber.log.Timber

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Handler().postDelayed({
            if (getBoolean(spIsLogin)) {
                Timber.d("已经登陆过")
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Timber.d("还没登陆过")
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 200)
    }
}
