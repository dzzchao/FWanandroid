package com.dzzchao.fwanandroid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzzchao.fwanandroid.retrofit.RetrofitHelper
import com.dzzchao.fwanandroid.retrofit.bean.SearchData
import com.dzzchao.fwanandroid.retrofit.bean.SearchDatas
import com.dzzchao.fwanandroid.retrofit.bean.SearchHotWordsResp
import com.dzzchao.fwanandroid.retrofit.bean.SearchKeyWordsResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel : ViewModel() {

    private val _hotWordsData = MutableLiveData<SearchHotWordsResp>()
    val hotWordsData: LiveData<SearchHotWordsResp> = _hotWordsData


    private val _searchKeyWordsData = MutableLiveData<SearchKeyWordsResp>()
    val searchKeyWordsData: LiveData<SearchKeyWordsResp> = _searchKeyWordsData

    fun requestSearchHot() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchHotWords = RetrofitHelper.retrofitService.getSearchHotWords()
                _hotWordsData.postValue(searchHotWords)
            } catch (e: Exception) {
                _hotWordsData.postValue(SearchHotWordsResp(-1, "", null))
            }
        }
    }

    fun searchKeyWords(keyWords: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchKeyWordsResp = RetrofitHelper.retrofitService.searchKeyWords(keyWords)
                _searchKeyWordsData.postValue(searchKeyWordsResp)
            } catch (e: Exception) {
                Timber.e("搜索请求出错： $e")
                e.printStackTrace()
                _searchKeyWordsData.postValue(SearchKeyWordsResp(-1, "", SearchData(-1, null)))
            }
        }
    }

}