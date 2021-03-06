package com.dzzchao.fwanandroid.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.data.LoginRepository
import com.dzzchao.fwanandroid.data.Result
import com.dzzchao.fwanandroid.storage.sp.SPHelper
import com.dzzchao.fwanandroid.storage.sp.spIsLogin
import com.dzzchao.fwanandroid.storage.sp.spPassword
import com.dzzchao.fwanandroid.storage.sp.spUserName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    /**
     * 登录方法
     */
    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = loginRepository.login(username, password)
                withContext(Dispatchers.Main) {
                    if (result is Result.Success) {
                        _loginResult.value =
                            LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
                        SPHelper.putString(spUserName, username)
                        SPHelper.putString(spPassword, username)
                        SPHelper.putBoolean(spIsLogin, true)
                    } else {
                        _loginResult.value = LoginResult(error = R.string.login_failed)
                    }
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}