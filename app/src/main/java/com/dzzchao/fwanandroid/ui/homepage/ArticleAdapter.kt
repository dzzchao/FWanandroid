package com.dzzchao.fwanandroid.ui.homepage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dzzchao.fwanandroid.App
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.retrofit.bean.Datas
import com.dzzchao.fwanandroid.webview.WebViewActivity
import timber.log.Timber

private const val TYPE_NORMAL = 1
private const val TYPE_FOOT = 2

const val INTENT_LINK = "INTENT_LINK"

/**
 * 文章列表
 */
class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = mutableListOf<ArticleShowData>()
    private var isMore = true;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        Timber.d("onCreateViewHolder() + viewType: $viewType")

        val itemView = LayoutInflater.from(parent.context).run {
            inflate(R.layout.home_article_item, parent, false)
        }

        val footView = LayoutInflater.from(parent.context).run {
            inflate(R.layout.home_footview, parent, false)
        }

        return if (viewType == TYPE_NORMAL) {
            ArticleViewHolder(itemView, dataList)
        } else {
            FootViewHolder(footView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != dataList.size) {
            TYPE_NORMAL
        } else {
            TYPE_FOOT
        }
    }

    override fun getItemCount(): Int {
        Timber.v("getItemCount()")
        return dataList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Timber.v("onBindViewHolder()")
        if (position != dataList.size) {
            holder as ArticleViewHolder
            val dataBean = dataList.get(position)
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

        } else {
            holder as FootViewHolder
            if (isMore) {
                holder.itemView.visibility = View.VISIBLE
                holder.tvFootView.text = App.context.getString(R.string.loading)
            } else {
                holder.itemView.visibility = View.GONE
            }
        }
    }

    fun updateData(tempList: MutableList<ArticleShowData>, hasMore: Boolean = true) {
        dataList.addAll(tempList)
        notifyDataSetChanged()
    }

}

class ArticleViewHolder(
    itemView: View,
    dataList: MutableList<ArticleShowData>
) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    val tvAuthor = itemView.findViewById<TextView>(R.id.tvAuthor)
    val tvTop = itemView.findViewById<TextView>(R.id.tvTop)

    init {
        Timber.v("ArticleViewHolder()")
        itemView.setOnClickListener {
            val data = dataList.get(adapterPosition)
            itemView.context.let {
                val intent = Intent(it, WebViewActivity::class.java)
                intent.putExtra(INTENT_LINK, data.link)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                it.startActivity(intent)
            }
        }
    }
}

class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvFootView = itemView.findViewById<TextView>(R.id.tv_foot_view)

    init {
        Timber.v("FootViewHolder()")
    }
}