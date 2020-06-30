package com.dzzchao.fwanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class BaseViewBindingFragment<T : ViewBinding> : BaseFragment() {

    private var mBinding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val superclass: Type = javaClass.genericSuperclass!!
        val bindingClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method: Method = bindingClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        return if (method.invoke(null, layoutInflater) is ViewBinding) {
            mBinding = method.invoke(null, layoutInflater) as T
            (mBinding?.root)
        } else {
            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}