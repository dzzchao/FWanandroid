package com.dzzchao.fwanandroid.retrofit.bean

data class SearchKeyWordsResp(
    val errorCode: Int,
    val errorMsg: String,
    val data: SearchData
)

data class SearchData(
    val curPage:Int,
    val datas:List<SearchDatas>?
)

data class SearchDatas(
    val link: String,
    val title: String,
    val shareUser: String,
    val author: String,
    val chapterName: String
)