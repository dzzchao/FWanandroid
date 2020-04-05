package com.dzzchao.fwanandroid.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzzchao.fwanandroid.retrofit.RetrofitHelper
import com.dzzchao.fwanandroid.retrofit.bean.ArticleTopResp
import com.dzzchao.fwanandroid.retrofit.bean.Datas
import com.dzzchao.fwanandroid.retrofit.bean.HomeArticleResp
import com.dzzchao.fwanandroid.retrofit.bean.HomeBannerResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var currentPage = 0

    val dataList = mutableListOf<String>()
    val articleList = mutableListOf<ArticleShowData>()

    private val _bannerData = MutableLiveData<HomeBannerResp>()
    val bannerData: LiveData<HomeBannerResp> = _bannerData


    //主界面的文章列表 - 第一次
    private val _homeData = MutableLiveData<HomeData>()
    val homeData: LiveData<HomeData> = _homeData


    private val _articleResp = MutableLiveData<HomeArticleResp>()
    val articleResp: LiveData<HomeArticleResp> = _articleResp


    fun requestBannerData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bannerResp = RetrofitHelper.retrofitService.getBanner()
                _bannerData.postValue(bannerResp)
            } catch (e: Exception) {
                _bannerData.postValue(HomeBannerResp(-1, "request error - banner", null))
            }
        }
    }


    /**
     * 第一次请求
     */
    fun requestArticleDataFirst(page: Int = 0) {
        viewModelScope.launch {
            val articleRespD =
                async(Dispatchers.IO) { RetrofitHelper.retrofitService.getHomeArticle(page) }
            val articleTopRespD =
                async(Dispatchers.IO) { RetrofitHelper.retrofitService.getHomeArticleTop() }

            val articleResp = articleRespD.await()
            val articleTopResp = articleTopRespD.await()

            _homeData.postValue(HomeData(articleResp, articleTopResp))
        }
    }

    fun requestArticleData(page: Int = 0) {
        viewModelScope.launch {
            val articleRespD =
                async(Dispatchers.IO) { RetrofitHelper.retrofitService.getHomeArticle(page) }
            val articleResp = articleRespD.await()
            _articleResp.postValue(articleResp)
        }
    }
}


data class HomeData(
    val homeArticleResp: HomeArticleResp,
    val articletopResp: ArticleTopResp
)

data class ArticleShowData(
    var title: String,
    var link: String,
    var author: String?,
    var shareUser: String?,
    var isTop: Boolean = false
)
