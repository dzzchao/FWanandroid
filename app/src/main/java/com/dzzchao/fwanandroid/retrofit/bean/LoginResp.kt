package com.dzzchao.fwanandroid.retrofit.bean

data class LoginResp(
    var errorCode: Int,
    var errorMsg: String?,
    var data: Data
) {
    data class Data(
        var id: Int,
        var username: String,
        var nickname: String,
        var publicName: String,
        var password: String,
        var icon: String?,
        var type: Int,
        var collectIds: List<Int>?,
        var token: String
    )
}