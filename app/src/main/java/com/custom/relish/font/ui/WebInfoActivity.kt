package com.custom.relish.font.ui

import android.content.Context
import android.content.Intent
import com.custom.relish.font.CustomFontApp
import com.custom.relish.font.R
import com.custom.relish.font.base.RootActivity
import com.custom.relish.font.databinding.AcWebInfoBinding
import com.custom.relish.font.utils.BarUtil

/**
 *author: hss
 *date: 2024/5/15 15:19
 *class desc:
 **/
class WebInfoActivity : RootActivity<AcWebInfoBinding>() {

    companion object {
        private const val ENTER_TYPE = "enter_type"
        const val PRIVACY = 1
        const val TERMS = 2
        private val PRIVACY_URL =
            "https://myfonts.notion.site/Privacy-Policy-99ca251ae68243afa9e662a8fcbeab40"
        private val TERMS_URL =
            "https://myfonts.notion.site/Terms-of-Service-b373fd4efc2c47209961c2de32d4157f"

        fun gotoWeb(context: Context, type: Int) {
            val intent = Intent(context, WebInfoActivity::class.java)
            intent.putExtra(ENTER_TYPE, type)
            context.startActivity(intent)
        }

    }

    override fun initView() {
        binding.idTopSpace.layoutParams.height =
            BarUtil.getStatusBarHeight() + CustomFontApp.instances.dp2px(10f)

        binding.idWebBack.setOnClickListener {
            finish()
        }
        binding.idWebView.setMixedContentAllowed(false)
        val intExtra = intent.getIntExtra(ENTER_TYPE, -1)
        if (intExtra == -1) {
            return
        }
        if (intExtra == PRIVACY) {
            binding.idWebTitle.text = getString(R.string.str_privacy_policy)
            binding.idWebView.loadUrl(PRIVACY_URL)
        } else if (intExtra == TERMS) {
            binding.idWebTitle.text = getString(R.string.str_terms_service)
            binding.idWebView.loadUrl(TERMS_URL)
        }
    }

}