package com.dzzchao.fwanandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
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

        changeFragment()

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
                    changeFragment(it.position)
                }
            }
        })
    }

    //切换fragment
    private fun changeFragment(position: Int = 0) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        when (position) {
            0 -> {
                beginTransaction.replace(R.id.frameLayout, HomeFragment.newInstance())
            }
            1 -> {
                beginTransaction.replace(R.id.frameLayout, TreeFragment.newInstance())
            }
            2 -> {
                beginTransaction.replace(R.id.frameLayout, FavoriteFragment.newInstance())
            }
            3 -> {
                beginTransaction.replace(R.id.frameLayout, UserFragment.newInstance())
            }
        }
        beginTransaction.commit()
    }
}