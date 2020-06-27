package com.dzzchao.fwanandroid.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

const val BASE_URL = "https://www.wanandroid.com"

const val CODE_SUCCESS = 0

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
            .baseUrl(BASE_URL)
            .client(myOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
    }

}

