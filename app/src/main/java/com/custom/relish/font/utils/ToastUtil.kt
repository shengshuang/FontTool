package com.custom.relish.font.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.custom.relish.font.R

/**
 *author: hss
 *date: 2024/4/24 11:58
 *class desc:
 **/
object ToastUtil {

    fun showContentToastView(context: Context, iconResId: Int, titleStr: String) {
        val layout: View = LayoutInflater.from(context)
            .inflate(R.layout.layout_toast_common, null)
        // 创建 Toast 并设置自定义布局
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        layout.findViewById<ImageView>(R.id.id_iv_top).setImageResource(iconResId)
        layout.findViewById<TextView>(R.id.id_tv_des).text = titleStr
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


}