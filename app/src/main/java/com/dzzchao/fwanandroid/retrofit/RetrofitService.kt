package com.dzzchao.fwanandroid.retrofit

import com.dzzchao.fwanandroid.retrofit.bean.LoginResp
import com.dzzchao.fwanandroid.retrofit.bean.LogoutResp
import com.dzzchao.fwanandroid.retrofit.bean.RegisterResp
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResp

    @POST("user/register")
    fun register(username: String, password: String, repassword: String): Call<RegisterResp>

    @GET("user/logout/json")
    fun logout(): Call<LogoutResp>

}