package com.custom.relish.font

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.custom.relish.font.modle.FontDataModelManager

/**
 *author: hss
 *date: 2024/5/15 13:48
 *class desc:
 **/
class CustomFontApp : MultiDexApplication() {

    companion object {
        @get:Synchronized
        lateinit var instances: CustomFontApp
    }

    override fun onCreate() {
        super.onCreate()
        instances = this
        FontDataModelManager.initData()
    }

    fun dp2px(dipValue: Float): Int {
        val scale = this.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

}