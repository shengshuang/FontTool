package com.custom.relish.font.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.custom.relish.font.utils.BarUtil
import java.lang.reflect.ParameterizedType

/**
 *author: hss
 *date: 2024/5/15 13:45
 *class desc:
 **/
abstract class RootActivity<T : ViewBinding> : FragmentActivity()  {

    protected val binding: T by lazy {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        BarUtil.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtil.setStatusBarLightMode(this, isLightMode())
        initView()
    }

    abstract fun  initView()

    protected fun isLightMode():Boolean{
        return true
    }
}