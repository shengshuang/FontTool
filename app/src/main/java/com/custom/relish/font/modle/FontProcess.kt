package com.custom.relish.font.modle

import androidx.annotation.Keep
import com.custom.relish.font.R
import com.custom.relish.font.utils.LogUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *author: hss
 *date: 2024/5/16 15:27
 *class desc:
 **/
//font 单个内容的容器
@Keep
data class FontItemBean(
    //db params
    val CODE: String,
    val ENABLED: String,
    val MAP: String,
    val NAME: String,
    //new params
    var mapList: MutableList<ContentData>?,
    var isSelected: Boolean = false,
) : IFontBaseData {

    fun convertFontMapData() {
        if (MAP.isNullOrEmpty() || MAP == "{}" || !MAP.startsWith("{") || !MAP.endsWith("}")) return
        if (mapList == null) {
            mapList = mutableListOf()
        }
        mapList?.clear()
        MAP.substring(1, MAP.length - 1).split(",").forEach { item ->
            if (item.contains(":")) {
                item.split(":").toTypedArray().let {
                    LogUtil.d("ceshi", "  array:: ${Gson().toJson(it)}")
                    mapList?.add(ContentData(getKeyValueContent(it[0]), getKeyValueContent(it[1])))
                }
            }
        }
        LogUtil.d("ceshi", "  array:: ${Gson().toJson(mapList)}")
    }

    private fun getKeyValueContent(contentStr: String): String {
        return if (contentStr.startsWith("\"") && contentStr.endsWith("\"") && contentStr.length > 2) {
            contentStr.substring(1, contentStr.length - 1)
        } else {
            contentStr
        }
    }

}

// 定义一个数据类，用于映射Json中的内容字段
@Keep
data class ContentData(val key: String, val value: String)


object FontDataHelper : IFontProcess {

    private var fontDataList = mutableListOf<FontItemBean>()

    override fun initData() {
        val arrType = object : TypeToken<MutableList<FontItemBean>>() {}.type
        val fontJson = getRawJsonText(R.raw.font)
        fontDataList = Gson().fromJson(fontJson, arrType)
        fontDataList.map {
            it.convertFontMapData()
            it.isSelected = false
        }
    }

    override fun getFontData(): MutableList<out IFontBaseData> {
        if (fontDataList.isNullOrEmpty()) {
            initData()
        }
        return fontDataList.filter { it.NAME != "-Normal Font-" } as MutableList<out IFontBaseData>
    }

}



