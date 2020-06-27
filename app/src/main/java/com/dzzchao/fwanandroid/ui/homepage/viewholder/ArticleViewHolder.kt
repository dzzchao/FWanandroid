package com.dzzchao.fwanandroid.ui.homepage.viewholder

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.ui.homepage.ArticleShowData
import com.dzzchao.fwanandroid.ui.homepage.INTENT_LINK
import com.dzzchao.fwanandroid.webview.WebViewActivity
import timber.log.Timber

/**
 * 首页文章列表
 */
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