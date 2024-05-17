package com.custom.relish.font.modle

/**
 *author: hss
 *date: 2024/5/16 15:51
 *class desc:
 **/
object FontDataModelManager {

    fun initData() {
        FontDataHelper.initData()
        EmojiDataHelper.initData()
    }

    fun getFontData(): MutableList<FontItemBean> {
        return FontDataHelper.getFontData() as MutableList<FontItemBean>
    }

    fun getEmojiData(): MutableList<EmojiItemBean> {
        return EmojiDataHelper.getFontData() as MutableList<EmojiItemBean>
    }

}