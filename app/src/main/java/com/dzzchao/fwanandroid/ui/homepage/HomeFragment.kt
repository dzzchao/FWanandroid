package com.dzzchao.fwanandroid.ui.homepage

import android.os.Bundle
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
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.utils.showToast
import kotlinx.android.synthetic.main.home_fragment.*
import timber.log.Timber

class HomeFragment : Fragment(R.layout.home_fragment) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

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

        //设置recyclerView
        val linearLayoutManager = LinearLayoutManager(context)
        rcvAritcle.layoutManager = linearLayoutManager
        val articleAdapter = ArticleAdapter()
        rcvAritcle.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rcvAritcle.adapter = articleAdapter

        //监听scroll
        rcvAritcle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if(lastItemPosition == articleAdapter.itemCount - 1) {
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