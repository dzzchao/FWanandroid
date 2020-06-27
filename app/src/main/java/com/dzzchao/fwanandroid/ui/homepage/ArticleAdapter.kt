package com.dzzchao.fwanandroid.ui.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dzzchao.fwanandroid.App
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.databinding.ItemHomeBannerBinding
import com.dzzchao.fwanandroid.ui.homepage.viewholder.ArticleViewHolder
import com.dzzchao.fwanandroid.ui.homepage.viewholder.FootViewHolder
import com.dzzchao.fwanandroid.ui.homepage.viewholder.HomeRvBannerViewHolder
import timber.log.Timber
import javax.validation.constraints.Null


private const val TYPE_HEAD = 625
private const val TYPE_NORMAL = 626
private const val TYPE_FOOT = 627

const val INTENT_LINK = "INTENT_LINK"

/**
 * 文章列表
 * head：banner
 * middle：article
 * footer：loading more
 */
class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mBannerList = mutableListOf<String>()
    private var dataList = mutableListOf<ArticleShowData>()
    private var isMore = true;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Timber.d("onCreateViewHolder() + viewType: $viewType")

        val bannerBinding =
            ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val itemView = LayoutInflater.from(parent.context).run {
            inflate(R.layout.home_article_item, parent, false)
        }

//        val footView = LayoutInflater.from(parent.context).run {
//            inflate(R.layout.home_footview, parent, false)
//        }


        return when (viewType) {
            TYPE_HEAD -> HomeRvBannerViewHolder(bannerBinding)
            TYPE_NORMAL -> ArticleViewHolder(itemView, dataList)

//            TYPE_FOOT -> FootViewHolder(footView)
            else -> ArticleViewHolder(itemView, dataList) //meaningless
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> {
                TYPE_HEAD
            }
//            position > dataList.size -> {
//                TYPE_FOOT
//            }
            else -> {
                TYPE_NORMAL
            }
        }
    }

    override fun getItemCount(): Int {
        Timber.v("getItemCount()")
        //add head&foot
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Timber.v("onBindViewHolder($position)")

        when (getItemViewType(position)) {
            TYPE_HEAD -> {
                holder as HomeRvBannerViewHolder
                val bannerAdapter = BannerAdapter(mBannerList)
                holder.binding.vpBanner.adapter = bannerAdapter
            }
            TYPE_NORMAL -> {
                holder as ArticleViewHolder
                val dataBean = dataList[position]
                holder.tvTitle.text = dataBean.title
                if (!dataBean.author.isNullOrEmpty()) {
                    holder.tvAuthor.text = dataBean.author
                } else {
                    holder.tvAuthor.text = dataBean.shareUser
                }

                //处理置顶文字样式
                if (dataBean.isTop) {
                    holder.tvTop.visibility = View.VISIBLE
                } else {
                    holder.tvTop.visibility = View.GONE
                }
            }
            TYPE_FOOT -> {
                holder as FootViewHolder
                if (isMore) {
                    holder.itemView.visibility = View.VISIBLE
                    holder.tvFootView.text = App.context.getString(R.string.loading)
                } else {
                    holder.itemView.visibility = View.GONE
                }
            }

        }

    }

    fun updateData(
        tempList: MutableList<ArticleShowData>,
        bannerList: MutableList<String>,
        hasMore: Boolean = true
    ) {
        Timber.d("updateData")
        dataList = tempList
        mBannerList = bannerList
        notifyDataSetChanged()
        isMore = hasMore
    }

    fun updateBannerData(bannerDataList: MutableList<String>) {
        this.mBannerList.addAll(bannerDataList)
    }

}