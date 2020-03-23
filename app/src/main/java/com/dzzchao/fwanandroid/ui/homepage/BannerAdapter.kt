package com.dzzchao.fwanandroid.ui.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dzzchao.fwanandroid.R

class BannerAdapter(val dataList: List<String>) : RecyclerView.Adapter<BannerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.home_banner_item, parent, false)
        return BannerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        Glide.with(holder.imageView.context).load(dataList.get(position)).into(holder.imageView)
    }

}

class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView = itemView.findViewById<ImageView>(R.id.itemImv)
}