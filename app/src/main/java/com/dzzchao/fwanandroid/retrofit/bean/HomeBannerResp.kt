package com.dzzchao.fwanandroid.retrofit.bean


data class HomeBannerResp(
    //0代表正常
    var errorCode: Int,
    var errorMsg: String?,
    var data: List<Data>?
) {
    data class Data(
        var id: Int,
        var url: String,
        var imagePath: String,
        var title: String,
        var desc: String,
        var isVisible: Int,
        var order: Int,
        var type: Int
    )
}