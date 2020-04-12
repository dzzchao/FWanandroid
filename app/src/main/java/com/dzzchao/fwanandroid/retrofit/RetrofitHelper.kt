package com.dzzchao.fwanandroid.retrofit

import com.dzzchao.fwanandroid.BASEURL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import kotlin.math.log

class RetrofitHelper {

    companion object {

        private fun myOkhttpClient(): OkHttpClient {
            val loggerInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.i("HTTPINFO --> $message")
                }
            })
            loggerInterceptor.run {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return OkHttpClient.Builder().addInterceptor(loggerInterceptor).build()
        }


        private val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(myOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)


    }

}

