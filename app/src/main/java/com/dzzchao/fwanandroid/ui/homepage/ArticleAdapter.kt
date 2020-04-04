package com.dzzchao.fwanandroid.ui.homepage

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dzzchao.fwanandroid.App
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.retrofit.bean.Datas
import timber.log.Timber

private const val TYPE_NORMAL = 1
private const val TYPE_FOOT = 2

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = mutableListOf<Datas>()
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
            ArticleViewHolder(itemView)
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
        } else {
            holder as FootViewHolder
            if (isMore) {
                holder.footView.visibility = View.VISIBLE
                holder.tvFootView.text = App.context.getString(R.string.loading)
            } else {
                holder.footView.visibility = View.GONE
            }
        }
    }

    fun updateData(tempList: MutableList<Datas>, hasMore: Boolean = true) {
        dataList.addAll(tempList)
        notifyDataSetChanged()
    }
}

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    val tvAuthor = itemView.findViewById<TextView>(R.id.tvAuthor)

    init {
        Timber.v("ArticleViewHolder()")
    }
}

class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvFootView = itemView.findViewById<TextView>(R.id.tv_foot_view)
    val footView = itemView.findViewById<LinearLayout>(R.id.footView)


    init {
        Timber.v("FootViewHolder()")
    }
}
