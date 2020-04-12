package com.dzzchao.fwanandroid.retrofit.bean

data class SearchHotWordsResp(
    val errorCode: Int,
    val errorMsg: String,
    val data: List<SearchHotDataBean>?
)


data class SearchHotDataBean(
    val id: Int,
    val name: String,
    val order: String
)