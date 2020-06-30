package com.dzzchao.fwanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

open class BaseViewBindingActivity<T : ViewBinding> : BaseActivity() {

    protected lateinit var mBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val superclass: Type = javaClass.genericSuperclass!!
        val bindingClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method: Method = bindingClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        if (method.invoke(null, layoutInflater) is ViewBinding) {
            mBinding = method.invoke(null, layoutInflater) as T
            setContentView(mBinding.root)
        }

    }

}