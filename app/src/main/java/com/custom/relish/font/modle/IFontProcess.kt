package com.custom.relish.font.modle

import android.content.Context
import com.custom.relish.font.CustomFontApp

import java.io.InputStream


/**
 *author: hss
 *date: 2024/5/16 15:34
 *class desc:
 **/
interface IFontProcess {

    fun initData()

    fun getFontData(): MutableList<out IFontBaseData>

    fun getRawJsonText(textFileResId: Int): String {
        val inputStream: InputStream = CustomFontApp.instances.resources.openRawResource(textFileResId)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }

}