package com.dzzchao.fwanandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dzzchao.fwanandroid.R
import com.dzzchao.fwanandroid.ui.favorite.FavoriteFragment
import com.dzzchao.fwanandroid.ui.homepage.HomeFragment
import com.dzzchao.fwanandroid.ui.tree.TreeFragment
import com.dzzchao.fwanandroid.ui.user.UserFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabs = arrayListOf(
            TabData("首页", R.drawable.ic_home_black_24dp) { HomeFragment.newInstance() },
            TabData("体系", R.drawable.ic_menu_black_24dp) { TreeFragment.newInstance() },
            TabData("收藏", R.drawable.ic_favorite_black_24dp) { FavoriteFragment.newInstance() },
            TabData("我的", R.drawable.ic_person_outline_black_24dp) { UserFragment.newInstance() }
        )

        vpMain.offscreenPageLimit = tabs.size
        vpMain.adapter = MainVpAdapter(this, tabs)
        vpMain.isUserInputEnabled = false


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                Timber.d("onTabReselected %s", tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Timber.d("onTabUnselected %s", tab?.position)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Timber.d("onTabSelected %s", tab?.position)
                tab?.let {
                    vpMain.setCurrentItem(it.position, false)
                }
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        //首帧开始的时间

    }

    data class TabData(
        val title: String,
        val icon: Int,
        val fragmentCreator: () -> Fragment
    )

}