package com.dzzchao.fwanandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dzzchao.fwanandroid.retrofit.bean.LoginResp
import com.dzzchao.fwanandroid.retrofit.RetrofitService
import io.reactivex.Scheduler
import io.reactivex.functions.Consumer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseUrl = "https://www.wanandroid.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTest.setOnClickListener {
        }
    }

//    private fun test() {
//        val retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .baseUrl(baseUrl)
//            .build()
//        val user = retrofit.create(RetrofitService::class.java)
//        user.login("zhangchao", "123456")
//            .subscribe({
//
//            }, {
//
//            })
//    }
}