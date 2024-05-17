package com.custom.relish.font.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.custom.relish.font.CustomFontApp
import com.custom.relish.font.modle.EmojiItemBean
import com.custom.relish.font.modle.FontItemBean
import com.custom.relish.font.modle.IFontBaseData

/**
 *author: hss
 *date: 2024/5/16 17:50
 *class desc:
 **/
class HomeVpAdapter(
    private val context: Context,
    private val dataMapData: Map<String, List<out IFontBaseData>>,
    private var itemFontClickCall: (itemBean: FontItemBean) -> Unit,
    private var itemEmojiClickCall: (itemBean: EmojiItemBean) -> Unit,
) :
    PagerAdapter() {

    companion object {
        const val KEY_FONT = "font"
        const val KEY_EMOJI = "emoji"
    }

    override fun getCount(): Int {
        return dataMapData.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rlView = RecyclerView(context)

        rlView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if (position == 0) {
            rlView.adapter =
                FontPickAdapter(
                    context,
                    dataMapData[KEY_FONT] as MutableList<FontItemBean>,
                    itemFontClickCall
                )
        } else {
            rlView.adapter =
                EmojiRlAdapter(
                    context,
                    dataMapData[KEY_EMOJI] as MutableList<EmojiItemBean>,
                    itemEmojiClickCall
                )
        }

        container.addView(rlView)
        return rlView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}