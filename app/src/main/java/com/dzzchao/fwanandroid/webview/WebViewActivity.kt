package com.dzzchao.fwanandroid.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.ui.homepage.INTENT_LINK
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val linkUrl = intent.getStringExtra(INTENT_LINK)
        webView.loadUrl(linkUrl)

    }
}