package com.custom.relish.font.ui

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.custom.relish.font.CustomFontApp
import com.custom.relish.font.R
import com.custom.relish.font.adapter.HomeVpAdapter
import com.custom.relish.font.base.RootActivity
import com.custom.relish.font.databinding.AcMainBinding
import com.custom.relish.font.modle.EmojiItemBean
import com.custom.relish.font.modle.FontDataModelManager
import com.custom.relish.font.modle.FontItemBean
import com.custom.relish.font.modle.IFontBaseData
import com.custom.relish.font.utils.BarUtil
import com.custom.relish.font.utils.FontUtil
import com.custom.relish.font.utils.ShareUtil
import com.custom.relish.font.utils.SoftKeyBordUtil
import com.custom.relish.font.utils.ToastUtil

/**
 *author: hss
 *date: 2024/5/15 14:02
 *class desc:
 **/
class MainActivity : RootActivity<AcMainBinding>() {

    private val fontDataList = FontDataModelManager.getFontData()
    private val emojiDataList = FontDataModelManager.getEmojiData()

    private var editTextInputContent: String = ""
    private var selectFontModel: FontItemBean? = null

    override fun initView() {
        bandViews()
        initListener()
    }

    private fun bandViews() {
        binding.idTopSp.layoutParams.height =
            BarUtil.getStatusBarHeight() + CustomFontApp.instances.dp2px(10f)
        //band rl
        binding.idVpFont.let {
            val mapData = mutableMapOf<String, List<out IFontBaseData>>()
            mapData[HomeVpAdapter.KEY_FONT] = fontDataList
            mapData[HomeVpAdapter.KEY_EMOJI] = emojiDataList
            it.adapter =
                HomeVpAdapter(this, mapData, itemFontClickCall, itemEmojiClickCall)
            it.addOnPageChangeListener(vpPagerListener)
        }
    }

    private fun initListener() {
        binding.idIvSetting.setOnClickListener {
            if (SoftKeyBordUtil.isSoftShowing(this)) {
                SoftKeyBordUtil.hideSoftKeyboard(this, binding.idEditView)
            }
            startActivity(
                Intent(
                    this,
                    SettingActivity::class.java
                )
            )
        }
        binding.idFontSwitch.setClickCall {
            binding.idVpFont.setCurrentItem(if (it) 0 else 1, true)
        }
        binding.idIvShare.setOnClickListener {
            if (binding.idEditView.text.isNullOrEmpty())return@setOnClickListener
            ShareUtil.shareText(this,binding.idEditView.text.toString())
        }
        binding.idTvCopy.setOnClickListener {
            val content = binding.idEditView.text.toString()
            if (!TextUtils.isEmpty(content)) {
                FontUtil.copyText(this, content)
                ToastUtil.showContentToastView(
                    this,
                    R.drawable.ic_right_white,
                    getString(R.string.str_copy_successfully)
                )
            }
        }
        binding.idIvClearFork.setOnClickListener {
            editTextInputContent = ""
            binding.idEditView.setText("")
        }
        binding.idEditView.addTextChangedListener(textWatcher)
    }

    private val vpPagerListener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int,
        ) {

        }

        override fun onPageSelected(position: Int) {
            binding.idFontSwitch.updateSwitchLayout(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
        }

    }

    /**
     * EditView 输入框文本监听器
     */
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            editTextInputContent = s.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (before == 0 && count > 0) {
                val afterText = s.toString()
                val addWord = afterText.substring(start, start + count)//摘出需要替换的字符
                val convertFont = if (addWord.length > 1) {
                    val stringBuilder = StringBuilder()
                    for (element in addWord) {
                        stringBuilder.append(getConvertFont(element.toString()))
                    }
                    stringBuilder.toString()
                } else {
                    getConvertFont(addWord)
                }
                coverEdittext(
                    start + convertFont.length,
                    afterText.replaceRange(start, start + addWord.length, convertFont)
                )

            }
        }

        override fun afterTextChanged(s: Editable?) {
            binding.idIvClearFork.visibility =
                if (TextUtils.isEmpty(binding.idEditView.text)) View.INVISIBLE else View.VISIBLE
        }

    }

    /**
     * 获取特殊字符转换
     */
    private fun getConvertFont(word: String): String {
        selectFontModel?.let {
            return try {
                it.mapList?.let {
                    var valueStr = ""
                    it.forEach {
                        if (it.key == word) {
                            valueStr = it.value
                        }
                    }
                    if (TextUtils.isEmpty(valueStr)) {
                        word
                    } else {
                        valueStr
                    }
                } ?: kotlin.run { word }
            } catch (ex: Exception) {
                word
            }
        } ?: kotlin.run {
            return word
        }
    }

    /**
     * 设置转换后的问题到EditText（解决setText调用监听器问题）
     */
    private fun coverEdittext(cursorIndex: Int, convertFont: String) {
        binding.idEditView.removeTextChangedListener(textWatcher)
        binding.idEditView.setText(convertFont)
        binding.idEditView.setSelection(cursorIndex)//光标移动到最后
        binding.idEditView.addTextChangedListener(textWatcher)
    }

    private val itemFontClickCall = { itemBean: FontItemBean ->
        this.selectFontModel = itemBean
        SoftKeyBordUtil.showSoftKeyboard(this, binding.idEditView)
        binding.idEditView.requestFocus()
        binding.idEditView.isCursorVisible = true
    }

    private val itemEmojiClickCall = { itemBean: EmojiItemBean ->
        val prefix = if (TextUtils.isEmpty(editTextInputContent)) {
            ""
        } else {
            "\n"
        }
        editTextInputContent += "$prefix${itemBean.Emoji}"
        binding.idEditView.setText(editTextInputContent)
        binding.idEditView.setSelection(binding.idEditView.text.length)
        binding.idEditView.requestFocus()
        binding.idEditView.isCursorVisible = true
    }

}