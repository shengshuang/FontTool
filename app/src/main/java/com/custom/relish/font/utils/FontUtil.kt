package com.custom.relish.font.utils

import android.content.ClipboardManager
import android.content.Context

/**
 *author: hss
 *date: 2024/5/15 18:23
 *class desc:
 **/
object FontUtil {

    fun copyText(context: Context, text: String) {
        val clip = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clip.text = text
    }

}