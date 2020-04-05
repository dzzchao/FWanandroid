package com.dzzchao.fwanandroid.ui.homepage

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.utils.showToast
import com.dzzchao.fwanandroid.view.TitleBar
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.handleCoroutineException
import timber.log.Timber

class HomeFragment : Fragment(R.layout.home_fragment) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var mHandler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.requestBannerData()
        viewModel.requestArticleData()

        val bannerAdapter = BannerAdapter(viewModel.dataList)
        vpBanner.adapter = bannerAdapter


        homeTitleBar.rightClickListener = object : TitleBar.OnRightClickListener {
            override fun click() {
                showToast("点击搜索")
            }
        }

        swipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                mHandler.postDelayed({ swipeRefresh.isRefreshing = false }, 2000)
            }
        })

        //设置recyclerView
        val linearLayoutManager = LinearLayoutManager(context)
        rcvAritcle.layoutManager = linearLayoutManager
        val articleAdapter = ArticleAdapter()
        rcvAritcle.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rcvAritcle.adapter = articleAdapter

        //监听scroll
        rcvAritcle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (lastItemPosition == articleAdapter.itemCount - 1) {
                    viewModel.requestArticleData(++viewModel.currentPage)
                }
            }
        })

        viewModel.bannerData.observe(viewLifecycleOwner, Observer {
            if (it.errorCode == 0) {
                //展示界面
                Timber.d("banner数据请求成功")
                it.data?.forEach {
                    viewModel.dataList.add(it.imagePath)
                }
                bannerAdapter.notifyDataSetChanged()
            } else {
                //请求失败
                showToast("Banner请求失败")
            }
        })

        viewModel.articleData.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = false
            if (it.errorCode == 0) {
                //展示界面
                viewModel.articleList.clear()
                it.data?.datas?.forEach {
                    viewModel.articleList.add(it)
                }
                articleAdapter.apply {
                    updateData(viewModel.articleList)
                }
            } else {
                //请求失败
                showToast("文章列表请求失败")
            }
        })
    }
}