package com.dzzchao.fwanandroid.retrofit

import com.dzzchao.fwanandroid.retrofit.bean.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    //首页

    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): HomeArticleResp

    @GET("banner/json")
    suspend fun getBanner(): HomeBannerResp

    @GET("article/top/json")
    suspend fun getHomeArticleTop(): ArticleTopResp

    @GET("hotkey/json")
    suspend fun getSearchHotWords(): SearchHotWordsResp

    //搜索

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun searchKeyWords(
        @Field("k") keywords: String,
        @Path("page") page: Int = 0
    ): SearchKeyWordsResp

    //用户相关

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResp>

    @POST("user/register")
    fun register(username: String, password: String, repassword: String): Call<RegisterResp>

    @GET("user/logout/json")
    fun logout(): Call<LogoutResp>
}