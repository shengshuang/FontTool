package com.custom.relish.font.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.custom.relish.font.R
import com.custom.relish.font.modle.FontItemBean

/**
 *author: hss
 *date: 2024/5/16 15:56
 *class desc:
 **/
class FontPickAdapter(
    private val context: Context,
    private val dataList: MutableList<FontItemBean>,
    private var itemClickCall: (itemBean: FontItemBean) -> Unit,
) :
    RecyclerView.Adapter<FontPickAdapter.FontPickViewHolder>() {

    inner class FontPickViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bandItemView(itemBean: FontItemBean) {
            itemView.findViewById<TextView>(R.id.id_tv_rl_item_font).text = itemBean.NAME

            itemView.findViewById<View>(R.id.id_iv_selected_bg).visibility =
                if (itemBean.isSelected) View.VISIBLE else View.INVISIBLE
            itemView.findViewById<View>(R.id.id_iv_checked).visibility =
                if (itemBean.isSelected) View.VISIBLE else View.INVISIBLE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontPickViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.layout_font_rl_item, parent, false)
        return FontPickViewHolder(itemView)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: FontPickViewHolder, position: Int) {
        holder.bandItemView(dataList[position])
        holder.itemView.setOnClickListener {
            if (dataList[position].isSelected) return@setOnClickListener
            dataList.forEachIndexed { index, fontItemBean ->
                fontItemBean.isSelected = position == index
            }
            notifyDataSetChanged()
            itemClickCall.invoke(dataList[position])
        }
    }

}