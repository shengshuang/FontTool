package com.custom.relish.font.utils

import android.content.Context
import android.content.Intent

/**
 *author: hss
 *date: 2024/5/17 09:54
 *class desc:
 **/
object ShareUtil {

    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, ""))
    }

}