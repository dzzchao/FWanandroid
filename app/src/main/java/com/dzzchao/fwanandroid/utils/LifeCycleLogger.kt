package com.dzzchao.fwanandroid.utils

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import timber.log.Timber
import kotlin.time.TimedValue

/**
 * 打印 Activity 和 Fragment 的生命周期
 */
class LifeCycleLogger {
    fun install(application: Application) {
        application.registerActivityLifecycleCallbacks(ActivityLifecycleCallback())
    }

    private inner class ActivityLifecycleCallback : ActivityLifecycleCallbacks {
        private val fragmentLifecycleCallback =
            FragmentLifecycleCallback()

        override fun onActivityCreated(
            activity: Activity,
            savedInstanceState: Bundle?
        ) {
            if (activity is FragmentActivity) {
                activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(fragmentLifecycleCallback, true)
            }
            log(activity, "onActivityCreated")
        }

        override fun onActivityStarted(activity: Activity) {
            log(activity, "onActivityStarted")
        }

        override fun onActivityResumed(activity: Activity) {
            log(activity, "onActivityResumed")
        }

        override fun onActivityPaused(activity: Activity) {
            log(activity, "onActivityPaused")
        }

        override fun onActivityStopped(activity: Activity) {
            log(activity, "onActivityStopped")
        }

        override fun onActivitySaveInstanceState(
            activity: Activity,
            outState: Bundle
        ) {
            log(activity, "onActivitySaveInstanceState")
        }

        override fun onActivityDestroyed(activity: Activity) {
            log(activity, "onActivityDestroyed")
        }
    }

    private inner class FragmentLifecycleCallback :
        FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentAttached(
            fm: FragmentManager,
            f: Fragment,
            context: Context
        ) {
            log(f, "onFragmentAttached")
        }

        override fun onFragmentCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            log(f, "onFragmentCreated")
        }

        override fun onFragmentActivityCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            log(f, "onFragmentActivityCreated")
        }

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            log(f, "onFragmentViewCreated")
        }

        override fun onFragmentStarted(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f, "onFragmentStarted")
        }

        override fun onFragmentResumed(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f, "onFragmentResumed")
        }

        override fun onFragmentPaused(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f, "onFragmentPaused")
        }

        override fun onFragmentStopped(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f, "onFragmentStopped")
        }

        override fun onFragmentSaveInstanceState(
            fm: FragmentManager,
            f: Fragment,
            outState: Bundle
        ) {
            log(f, "onFragmentSaveInstanceState")
        }

        override fun onFragmentViewDestroyed(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f, "onFragmentViewDestroyed")
        }

        override fun onFragmentDestroyed(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f, "onFragmentDestroyed")
        }

        override fun onFragmentDetached(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f, "onFragmentDetached")
        }
    }

    private fun log(any: Any, state: String) {
        Timber.v("${any.javaClass.simpleName} $state")
    }

}