package com.custom.relish.font.utils

import android.R
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import com.custom.relish.font.CustomFontApp

/**
 *author: hss
 *date: 2024/5/15 13:47
 *class desc:
 **/
object BarUtil {
    private const val TAG_STATUS_BAR = "TAG_STATUS_BAR"

    fun setStatusBarColor(activity: Activity, @ColorInt color: Int): View? {
        return setStatusBarColor(activity, color, false)
    }

    private fun setStatusBarColor(
        activity: Activity, @ColorInt color: Int, isDecor: Boolean,
    ): View? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return null
        transparentStatusBar(activity)
        return applyStatusBarColor(activity, color, isDecor)
    }

    private fun transparentStatusBar(activity: Activity) {
        transparentStatusBar(activity.window)
    }

    private fun applyStatusBarColor(activity: Activity, color: Int, isDecor: Boolean): View? {
        return applyStatusBarColor(activity.window, color, isDecor)
    }

    private fun transparentStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val option =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val vis = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = option or vis
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }


    private fun applyStatusBarColor(window: Window, color: Int, isDecor: Boolean): View {
        val parent =
            if (isDecor) window.decorView as ViewGroup else (window.findViewById<View>(R.id.content) as ViewGroup)
        var fakeStatusBarView = parent.findViewWithTag<View>(TAG_STATUS_BAR)
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.setBackgroundColor(color)
        } else {
            fakeStatusBarView = createStatusBarView(window.context, color)
            parent.addView(fakeStatusBarView)
        }
        return fakeStatusBarView
    }

    private fun createStatusBarView(context: Context, color: Int): View {
        val statusBarView = View(context)
        statusBarView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()
        )
        statusBarView.setBackgroundColor(color)
        statusBarView.tag = TAG_STATUS_BAR
        return statusBarView
    }

    fun setStatusBarLightMode(activity: Activity, isLightMode: Boolean) {
        setStatusBarLightMode(activity.window, isLightMode)
    }

    private fun setStatusBarLightMode(window: Window, isLightMode: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            var vis = decorView.systemUiVisibility
            vis = if (isLightMode) {
                vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            decorView.systemUiVisibility = vis
        }
    }

    /**
     * Return the status bar's height.
     *
     * @return the status bar's height
     */
    fun getStatusBarHeight(): Int {
        val resources: Resources = CustomFontApp.instances.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}