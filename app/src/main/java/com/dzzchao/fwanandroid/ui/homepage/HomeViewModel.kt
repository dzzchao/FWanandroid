package com.dzzchao.fwanandroid.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzzchao.fwanandroid.retrofit.RetrofitHelper
import com.dzzchao.fwanandroid.retrofit.bean.Datas
import com.dzzchao.fwanandroid.retrofit.bean.HomeArticleResp
import com.dzzchao.fwanandroid.retrofit.bean.HomeBannerResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var currentPage = 0

    val dataList = mutableListOf<String>()
    val articleList = mutableListOf<Datas>()

    private val _bannerData = MutableLiveData<HomeBannerResp>()
    val bannerData: LiveData<HomeBannerResp> = _bannerData

    private val _articleData = MutableLiveData<HomeArticleResp>()
    val articleData: LiveData<HomeArticleResp> = _articleData

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

    fun requestArticleData(page: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val articleResp = RetrofitHelper.retrofitService.getHomeArticle(page)
                _articleData.postValue(articleResp)
            } catch (e: Exception) {
                _articleData.postValue(HomeArticleResp(-1, "request error - article", null))
            }
        }
    }
}
