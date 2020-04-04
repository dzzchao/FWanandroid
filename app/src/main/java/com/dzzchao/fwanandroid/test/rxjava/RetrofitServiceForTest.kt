package com.dzzchao.fwanandroid.test.rxjava

import com.dzzchao.fwanandroid.retrofit.bean.HomeArticleResp
import com.dzzchao.fwanandroid.retrofit.bean.HomeBannerResp
import com.dzzchao.fwanandroid.retrofit.bean.LoginResp
import com.dzzchao.fwanandroid.retrofit.bean.RegisterResp
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitServiceForTest {

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<LoginResp>

    @POST("user/register")
    fun register(username: String, password: String, repassword: String): Observable<RegisterResp>


    @GET("article/list/{page}/json")
    fun getHomeArticle(@Path("page") page: Int = 0): Observable<HomeArticleResp>

    @GET("banner/json")
    fun getBanner(): Observable<HomeBannerResp>

}