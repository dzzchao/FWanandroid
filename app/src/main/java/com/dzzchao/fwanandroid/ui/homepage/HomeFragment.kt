package com.dzzchao.fwanandroid.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dzzchao.fwanandroid.BaseFragment
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.databinding.HomeFragmentBinding
import com.dzzchao.fwanandroid.retrofit.CODE_SUCCESS
import com.dzzchao.fwanandroid.ui.search.SearchActivity
import com.dzzchao.fwanandroid.utils.showToast
import com.dzzchao.fwanandroid.view.TitleBar
import timber.log.Timber

/**
 * banner数据单独与文章列表数据分开单独请求
 * 文章列表分为置顶文章和普通的文章列表，这两个同时请求，可以有多重方案来做 - (在这里我们用协程来做)
 */
class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var mBinding: HomeFragmentBinding
    private lateinit var mViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = HomeFragmentBinding.inflate(layoutInflater, container, false)
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        initView()
        initData()
        return mBinding.root
    }

    /**
     * 请求页面显示需要的数据
     */
    private fun initData() {
        //设置recyclerView
        val linearLayoutManager = LinearLayoutManager(context)
        mBinding.rcvArticle.layoutManager = linearLayoutManager
        //增加分割线
        mBinding.rcvArticle.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        val articleAdapter = ArticleAdapter()
        mBinding.rcvArticle.adapter = articleAdapter

        //请求banner+置顶+文章第一页的数据
        mViewModel.requestArticleData(isNeedBannerAndTop = true)
            .observe(viewLifecycleOwner, Observer {
                Timber.d("首次请求数据 observer")
                if (it.homeArticleResp.errorCode == 0 && it.articleTopResp?.errorCode == 0 && it.homeBannerResp?.errorCode == 0) {
                    //展示界面
                    mViewModel.articleList.clear()
                    //先添加置顶文章
                    it.articleTopResp.data.forEach { top ->
                        mViewModel.articleList.add(
                            ArticleShowData(
                                top.title,
                                top.link,
                                top.author,
                                top.shareUser,
                                true
                            )
                        )
                    }
                    //再添加普通文章
                    it.homeArticleResp.data?.datas?.forEach { data ->
                        mViewModel.articleList.add(
                            ArticleShowData(
                                data.title,
                                data.link,
                                data.author,
                                data.shareUser
                            )
                        )
                    }
                    it.homeBannerResp.data?.forEach { data ->
                        mViewModel.bannerDataList.add(data.imagePath)
                    }
                    articleAdapter.apply {
                        updateData(mViewModel.articleList,mViewModel.bannerDataList)
                    }

                } else {
                    //请求失败
                    showToast("文章列表请求失败")
                }
            })


    }

    /**
     * handle view 初始化显示
     * 数据无关的
     */
    private fun initView() {
        //标题栏的点击跳转
        mBinding.homeTitleBar.rightClickListener = object : TitleBar.OnRightClickListener {
            override fun click() {
                val intent = Intent(activity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
