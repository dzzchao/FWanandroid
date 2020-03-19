package com.dzzchao.fwanandroid.data

import com.dzzchao.fwanandroid.App
import com.dzzchao.fwanandroid.BASEURL
import com.dzzchao.fwanandroid.data.model.LoggedInUser
import com.dzzchao.fwanandroid.retrofit.RetrofitService
import com.dzzchao.fwanandroid.retrofit.bean.LoginResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {


    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
//            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
//            return Result.Success(fakeUser)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .build()
            val retrofitService = retrofit.create(RetrofitService::class.java)
            val response = retrofitService.login(username, password)
            Timber.d("loginHeader: %s", response.headers().toString())
            val user = response.body() as LoginResp
            return Result.Success(
                LoggedInUser(
                    user.data.id.toString(),
                    user.data.username,
                    user.data.password
                )
            )
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
