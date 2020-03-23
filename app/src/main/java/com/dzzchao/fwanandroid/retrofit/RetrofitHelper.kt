package com.dzzchao.fwanandroid.retrofit

import com.dzzchao.fwanandroid.BASEURL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEURL)
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
    }

}

