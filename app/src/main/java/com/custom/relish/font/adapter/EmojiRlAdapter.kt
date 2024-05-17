package com.custom.relish.font.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.custom.relish.font.R
import com.custom.relish.font.modle.EmojiItemBean

/**
 *author: hss
 *date: 2024/5/16 17:19
 *class desc:
 **/
class EmojiRlAdapter(
    private val context: Context,
    private val dataList: MutableList<EmojiItemBean>,
    private var itemClickCall: (itemBean: EmojiItemBean) -> Unit,
) : RecyclerView.Adapter<EmojiRlAdapter.EmojiPickViewHolder>() {

    inner class EmojiPickViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bandItemView(itemBean: EmojiItemBean) {
            itemView.findViewById<LinearLayout>(R.id.id_ll_emoji_list).let { llLayout ->
                itemBean.emojiList?.forEach {
                    addItemTextViewToContainer(it, llLayout)
                } ?: kotlin.run {
                    addItemTextViewToContainer(itemBean.Emoji, llLayout)
                }
            }
        }

        private fun addItemTextViewToContainer(emojiStr: String, llContainer: LinearLayout) {
            val itemTextView = TextView(context)
            itemTextView.text = emojiStr
            itemTextView.textSize = 8f
            itemTextView.setTextColor(ContextCompat.getColor(context, R.color.color_18102E))
            itemTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            itemTextView.gravity = Gravity.CENTER
            llContainer.addView(itemTextView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiPickViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.layout_emoji_rl_item, parent, false)
        return EmojiPickViewHolder(itemView).apply { setIsRecyclable(false) }
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: EmojiPickViewHolder, position: Int) {
        holder.bandItemView(dataList[position])
        holder.itemView.setOnClickListener {
            itemClickCall.invoke(dataList[position])
        }
    }

}