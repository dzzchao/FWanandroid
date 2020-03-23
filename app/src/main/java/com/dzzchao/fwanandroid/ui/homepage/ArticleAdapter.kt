package com.dzzchao.fwanandroid.ui.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.retrofit.bean.Datas

class ArticleAdapter(val dataList: List<Datas>) : RecyclerView.Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_article_item, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val dataBean = dataList.get(position)
        holder.tvTitle.text = dataBean.title
        if (!dataBean.author.isNullOrEmpty()) {
            holder.tvAuthor.text = dataBean.author
        } else {
            holder.tvAuthor.text = dataBean.shareUser
        }
    }
}

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    val tvAuthor = itemView.findViewById<TextView>(R.id.tvAuthor)
}