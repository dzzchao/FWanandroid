package com.dzzchao.fwanandroid.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.ui.search.SearchActivity
import com.dzzchao.fwanandroid.utils.showToast
import com.dzzchao.fwanandroid.view.TitleBar
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.home_fragment.*
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * banner数据单独与文章列表数据分开单独请求
 * 文章列表分为置顶文章和普通的文章列表，这两个同时请求，可以有多重方案来做 - (在这里我们用协程来做)
 */
class HomeFragment : Fragment(R.layout.home_fragment) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    private var mHandler = Handler()

    private var bannerRunnable = object : Runnable {
        override fun run() {
            vpBanner.setCurrentItem((vpBanner.currentItem + 1) % viewModel.dataList.size, true)
            mHandler.postDelayed(this, 2000)
        }
    }

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
        viewModel.requestArticleDataFirst()


        //banner
        val bannerAdapter = BannerAdapter(viewModel.dataList)
        vpBanner.adapter = bannerAdapter


        mHandler.postDelayed(bannerRunnable, 2000)

        homeTitleBar.rightClickListener = object : TitleBar.OnRightClickListener {
            override fun click() {
                val intent = Intent(activity, SearchActivity::class.java)
                startActivity(intent)
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

        //监听scroll,做上拉加载。
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

        //第一次请求文章列表的数据
        viewModel.homeData.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = false

            if (it.homeArticleResp.errorCode == 0) {
                //展示界面
                viewModel.articleList.clear()

                it.articletopResp.data.forEach { top ->
                    viewModel.articleList.add(
                        ArticleShowData(
                            top.title,
                            top.link,
                            top.author,
                            top.shareUser,
                            true
                        )
                    )
                }

                it.homeArticleResp.data?.datas?.forEach { data ->
                    viewModel.articleList.add(
                        ArticleShowData(
                            data.title,
                            data.link,
                            data.author,
                            data.shareUser
                        )
                    )
                }
                articleAdapter.apply {
                    updateData(viewModel.articleList)
                }
            } else {
                //请求失败
                showToast("文章列表请求失败")
            }
        })

        //后续的请求
        viewModel.articleResp.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = false
            if (it.errorCode == 0) {
                //展示界面
                viewModel.articleList.clear()
                it.data?.datas?.forEach { data ->
                    viewModel.articleList.add(
                        ArticleShowData(
                            data.title,
                            data.link,
                            data.author,
                            data.shareUser
                        )
                    )
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

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(bannerRunnable)
    }
}
