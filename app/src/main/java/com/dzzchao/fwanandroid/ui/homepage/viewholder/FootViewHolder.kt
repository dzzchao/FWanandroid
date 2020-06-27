package com.dzzchao.fwanandroid.ui.homepage.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dzzchao.fwanandroid.R
import timber.log.Timber

class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvFootView: TextView = itemView.findViewById(R.id.tv_foot_view)

    init {
        Timber.v("FootViewHolder()")
    }
}