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
import timber.log.Timber

class HomeViewModel : ViewModel() {

    var currentPage = 0

    val bannerDataList = mutableListOf<String>()
    val articleList = mutableListOf<ArticleShowData>()



    /**
     * 请求banner显示数据
     */
    fun requestBannerData(): LiveData<HomeBannerResp> {
        val liveData = MutableLiveData<HomeBannerResp>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bannerResp = RetrofitHelper.retrofitService.getBanner()
                liveData.postValue(bannerResp)
            } catch (e: Exception) {
                liveData.postValue(HomeBannerResp(-1, "request error - banner", null))
            }
        }
        return liveData
    }

    /**
     * 列表数据和置顶文章数据是两个接口
     * 尽量封装到一起
     *
     */

    /**
     * 第一次请求
     * @param isNeedBannerAndTop 是否需要返回置顶数据和置顶文章
     */
    fun requestArticleData(
        page: Int = 0,
        isNeedBannerAndTop: Boolean = false
    ): LiveData<HomeData> {
        Timber.d("requestArticleData $page")
        val liveData = MutableLiveData<HomeData>()

        viewModelScope.launch {
            val articleRespD =
                async(Dispatchers.IO) { RetrofitHelper.retrofitService.getHomeArticle(page) }
            val articleResp = articleRespD.await()

            if (isNeedBannerAndTop) {
                val articleTopRespD = async(Dispatchers.IO) {
                    RetrofitHelper.retrofitService.getHomeArticleTop()
                }
                val articleTopResp = articleTopRespD.await()

                val bannerRespD = async(Dispatchers.IO) {
                    RetrofitHelper.retrofitService.getBanner()
                }

                val bannerResp = bannerRespD.await()

                liveData.postValue(HomeData(articleResp, articleTopResp, bannerResp))
            } else {
                liveData.postValue(HomeData(articleResp, null, null))
            }
        }

        return liveData
    }

    /**
     * 分页数据
     */
//    fun requestArticleData(page: Int = 0) {
//        Timber.d("主界面列表加载更多 page = $page")
//        viewModelScope.launch {
//            val articleRespD =
//                async(Dispatchers.IO) { RetrofitHelper.retrofitService.getHomeArticle(page) }
//            val articleResp = articleRespD.await()
//            _articleResp.postValue(articleResp)
//        }
//    }
}


data class HomeData(
    val homeArticleResp: HomeArticleResp,
    val articleTopResp: ArticleTopResp?,
    val homeBannerResp: HomeBannerResp?
)

data class ArticleShowData(
    var title: String,
    var link: String,
    var author: String?,
    var shareUser: String?,
    var isTop: Boolean = false
)
