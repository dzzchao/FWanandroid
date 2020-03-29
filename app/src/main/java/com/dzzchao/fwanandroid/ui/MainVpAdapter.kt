package com.dzzchao.fwanandroid.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainVpAdapter(
    fragmentActivity: FragmentActivity,
    private val tabs: ArrayList<MainActivity.TabData>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return this.tabs.get(position).fragmentCreator()
    }



}