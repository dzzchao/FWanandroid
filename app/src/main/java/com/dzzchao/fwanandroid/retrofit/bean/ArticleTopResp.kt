package com.dzzchao.fwanandroid.retrofit.bean

data class ArticleTopResp(
    var errorCode: Int,
    var errorMsg: String,
    var data: List<TopData>
)

data class TopData(
    var author: String,
    var chapterName: String,
    var link: String,
    var title: String,
    var shareUser: String
)