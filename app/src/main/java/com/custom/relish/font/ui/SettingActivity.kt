package com.custom.relish.font.ui

import com.custom.relish.font.CustomFontApp
import com.custom.relish.font.base.RootActivity
import com.custom.relish.font.databinding.AcSettingBinding
import com.custom.relish.font.utils.BarUtil

/**
 *author: hss
 *date: 2024/5/15 14:03
 *class desc:
 **/
class SettingActivity : RootActivity<AcSettingBinding>() {

    override fun initView() {
        binding.idTopSp.layoutParams.height =
            BarUtil.getStatusBarHeight() + CustomFontApp.instances.dp2px(10f)

        binding.idIvSetBack.setOnClickListener {
            finish()
        }
        binding.idViewClickPrivacyPolicy.setOnClickListener {
            WebInfoActivity.gotoWeb(this, WebInfoActivity.PRIVACY)
        }
        binding.idViewClickTermsOfService.setOnClickListener {
            WebInfoActivity.gotoWeb(this, WebInfoActivity.TERMS)
        }

    }

}