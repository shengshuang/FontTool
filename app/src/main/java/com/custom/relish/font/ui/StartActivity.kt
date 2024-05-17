package com.custom.relish.font.ui

import android.content.Intent
import com.custom.relish.font.base.RootActivity
import com.custom.relish.font.databinding.AcStartBinding

/**
 *author: hss
 *date: 2024/5/15 13:55
 *class desc:
 **/
class StartActivity : RootActivity<AcStartBinding>() {

    override fun initView() {
        binding.idTvFont.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)

    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

}