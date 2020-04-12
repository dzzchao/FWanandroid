package com.dzzchao.fwanandroid.ui.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.ui.homepage.ArticleAdapter
import com.dzzchao.fwanandroid.ui.homepage.ArticleShowData
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.home_fragment.*
import timber.log.Timber

class SearchActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel
    private val showList = mutableListOf<ArticleShowData>()

    private var dataList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        showSearchListUI(false)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.requestSearchHot()

        searchView.run {
            isSubmitButtonEnabled = true
            setIconifiedByDefault(false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Timber.d("点击了搜索 $query")
                    query?.let {
                        search(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    //如果搜索框中没有文字，就显示搜索热词界面
                    if(newText != null && newText.isEmpty()) {
                        showSearchListUI(false)
                    }
                    return true
                }
            })
        }

        searchViewModel.hotWordsData.observe(this, Observer {
            it.run {
                if (errorCode == 0) {
                    data?.forEach {
                        dataList.add(it.name)
                    }
                    showHotWords()
                } else {
                    //请求出错
                    Timber.w("热词请求出错")
                }
            }
        })

        searchViewModel.searchKeyWordsData.observe(this, Observer {
            it.run {
                if (errorCode == 0) {
                    //分页数据
                    //< ArticleShowData>
                    showList.clear()
                    data.datas?.forEach {
                        showList.add(ArticleShowData(it.title, it.link, it.author, it.shareUser))
                    }
                    initRecyclerSearch();
                } else {
                    //请求出错
                    Timber.e("搜索结果出错")
                }
            }
        })
    }

    /**
     * 显示列表数据
     */
    private fun initRecyclerSearch() {
        showSearchListUI()

        val articleAdapter = ArticleAdapter()
        recycler_search_data.let {
            it.adapter = articleAdapter
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        articleAdapter.updateData(showList)


    }

    private fun showSearchListUI(flag: Boolean = true) {
        if (flag) {
            recycler_search_data.visibility = View.VISIBLE

            tv_everybody_search.visibility = View.GONE
            search_gridView.visibility = View.GONE
        } else {
            recycler_search_data.visibility = View.GONE

            tv_everybody_search.visibility = View.VISIBLE
            search_gridView.visibility = View.VISIBLE
        }
    }


    private fun showHotWords() {
        search_gridView.adapter =
            ArrayAdapter(this, R.layout.item_search_hotwords, dataList)
        search_gridView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val searchStr = dataList.get(position)
                search(searchStr)
            }
        })
    }


    /**
     * 处理搜索方法
     */
    private fun search(keywords: String) {
        Timber.d("搜索内容： $keywords")
        searchViewModel.searchKeyWords(keywords)
    }
}
