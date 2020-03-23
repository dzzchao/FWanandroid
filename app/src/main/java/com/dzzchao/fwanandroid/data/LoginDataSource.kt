package com.dzzchao.fwanandroid.data

import com.dzzchao.fwanandroid.data.model.LoggedInUser
import com.dzzchao.fwanandroid.retrofit.RetrofitHelper
import com.dzzchao.fwanandroid.retrofit.bean.LoginResp
import timber.log.Timber

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {


    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // TODO: handle loggedInUser authentication
//            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
//            return Result.Success(fakeUser)

        val response = RetrofitHelper.retrofitService.login(username, password)
        Timber.d("loginHeader: %s", response.headers().toString())
        val user = response.body() as LoginResp
        return Result.Success(
            LoggedInUser(
                user.data.id.toString(),
                user.data.username,
                user.data.password
            )
        )
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
