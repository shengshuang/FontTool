package com.custom.relish.font.modle

import androidx.annotation.Keep
import com.custom.relish.font.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *author: hss
 *date: 2024/5/16 15:27
 *class desc:
 **/
@Keep
data class EmojiItemBean(
    //db params
    val Category: String,
    val Emoji: String,
    val Height: String,
    //add new params
    var emojiList: List<String>? = null,
) : IFontBaseData {

    fun convertToEmojiList() {
        if (Emoji.isNullOrEmpty()) return
        emojiList = Emoji.split("\n")
    }

}

object EmojiDataHelper : IFontProcess {

    private var emojiDataList = mutableListOf<EmojiItemBean>()

    override fun initData() {
        val arrType = object : TypeToken<MutableList<EmojiItemBean>>() {}.type
        val fontJson = getRawJsonText(R.raw.face_emoji_art)
        emojiDataList = Gson().fromJson(fontJson, arrType)
        emojiDataList.map {
            it.convertToEmojiList()
        }

    }

    override fun getFontData(): MutableList<out IFontBaseData> {
        if (emojiDataList.isNullOrEmpty()) {
            initData()
        }
        return emojiDataList
    }

}